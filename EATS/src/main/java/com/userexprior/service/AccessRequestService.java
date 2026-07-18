package com.userexprior.service;

import com.userexprior.config.AccessPolicyConfig;
import com.userexprior.model.AccessRequest;
import com.userexprior.repository.AccessRequestRepository;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessRequestService {

    private final ChatClient chatClient;
    private final AccessPolicyConfig policyConfig;
    private final AccessRequestRepository repository;

    public AccessRequestService(ChatClient chatClient,
                                AccessPolicyConfig policyConfig,
                                AccessRequestRepository repository) {
        this.chatClient = chatClient;
        this.policyConfig = policyConfig;
        this.repository = repository;
    }

    public AccessRequest processRequest(AccessRequest request) {

        // Check duplicate request
        Optional<AccessRequest> existing =
                repository.findByUsernameAndResourceAndRequestedAccess(
                        request.getUsername(),
                        request.getResource(),
                        request.getRequestedAccess());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Access request already exists.");
        }

        List<String> allowedPermissions =
                policyConfig.getRoles().getOrDefault(request.getRole(), List.of());

        boolean alreadyAllowed =
                allowedPermissions.contains(request.getRequestedAccess());

        String prompt = buildPrompt(request, allowedPermissions, alreadyAllowed);

        String aiResponse = chatClient.call(prompt);

        request.setAiDecision(aiResponse);

        if (alreadyAllowed) {
            request.setStatus("AUTO_APPROVED");
        } else {
            request.setStatus("PENDING_APPROVAL");
        }

        request.setRiskLevel(calculateRisk(request.getRequestedAccess()));

        request.setPolicyVersion("1.0");

        return repository.save(request);
    }

    private String buildPrompt(AccessRequest request,
                               List<String> allowedPermissions,
                               boolean alreadyAllowed) {

        return """
                You are an Enterprise Access Governance AI.

                Current Role : %s
                Resource     : %s
                Requested    : %s

                Allowed Permissions:
                %s

                Already Allowed : %s

                Return ONLY valid JSON.

                {
                  "decision":"",
                  "reason":"",
                  "risk":"",
                  "confidence":0,
                  "recommendation":""
                }

                """
                .formatted(
                        request.getRole(),
                        request.getResource(),
                        request.getRequestedAccess(),
                        allowedPermissions,
                        alreadyAllowed);
    }

    private String calculateRisk(String permission) {

        return switch (permission) {

            case "APP_READ", "DOC_VIEW", "LOG_VIEW" -> "LOW";

            case "DB_READ", "REPORT_VIEW" -> "MEDIUM";

            case "DB_WRITE", "USER_MANAGE" -> "HIGH";

            case "SYSTEM_CONFIG" -> "CRITICAL";

            default -> "UNKNOWN";
        };
    }

    public List<AccessRequest> getAllRequests() {
        return (List<AccessRequest>) repository.findAll();
    }

    public Optional<AccessRequest> getRequestById(Long id) {
        return repository.findById(id);
    }

}