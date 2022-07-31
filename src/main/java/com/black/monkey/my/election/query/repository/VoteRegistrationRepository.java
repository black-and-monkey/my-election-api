package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.query.domain.VoteRegistration;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteRegistrationRepository extends CrudRepository<VoteRegistration, UUID> {

    Optional<VoteRegistration> findByCi(String ci);
}
