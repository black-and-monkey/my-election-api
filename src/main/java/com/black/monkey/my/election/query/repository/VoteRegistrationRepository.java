package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteRegistrationRepository extends CrudRepository<VoteRegistration, UUID> {

    Optional<VoteRegistration> findByCi(String ci);

    @Query("SELECT COUNT(v) FROM VoteRegistration v WHERE v.crv.id = ?1")
    long countByCrv(String crvId);

    @Query("SELECT v FROM VoteRegistration v WHERE v.crv.id = ?1")
    Page<BaseEntity> findByCrv(String crvId, PageRequest page);
}
