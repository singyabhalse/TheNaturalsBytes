package com.userexprior.controller;

import com.userexprior.model.AccessRequest;
import com.userexprior.service.AccessRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    public AccessRequestController(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }

    // POST endpoint to raise access request
    @PostMapping("/request")
    public ResponseEntity<AccessRequest> createAccessRequest(@RequestBody AccessRequest req) {
        AccessRequest processed = accessRequestService.processRequest(req);
        return ResponseEntity.ok(processed);
    }

    // GET endpoint to fetch all requests
    @GetMapping("/requests")
    public ResponseEntity<List<AccessRequest>> getAllRequests() {
        return ResponseEntity.ok(accessRequestService.getAllRequests());
    }

    // GET endpoint to fetch request by ID
    @GetMapping("/request/{id}")
    public ResponseEntity<AccessRequest> getRequestById(@PathVariable Long id) {
        return ResponseEntity.of(accessRequestService.getRequestById(id));
    }
}
