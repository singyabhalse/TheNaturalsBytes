package com.userexprior.model;

import jakarta.persistence.*;

@Entity
@Table(name = "access_requests")
public class AccessRequest {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String resource;
    private String requestedAccess;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String aiDecision;
    private String status;
    private String role;
    private String policyVersion;
    private String riskLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getRequestedAccess() {
        return requestedAccess;
    }

    public void setRequestedAccess(String requestedAccess) {
        this.requestedAccess = requestedAccess;
    }

    public String getAiDecision() {
        return aiDecision;
    }

    public void setAiDecision(String aiDecision) {
        this.aiDecision = aiDecision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
