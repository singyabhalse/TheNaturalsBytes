package com.userexprior.repository;

import com.userexprior.model.AccessRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRequestRepository extends CrudRepository<AccessRequest, Long> {
    Optional<AccessRequest> findByUsernameAndResourceAndRequestedAccess(String username, String resource, String requestedAccess);
}
