package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.domain.Crv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CrvRepository extends CrudRepository<Crv, String> {

    @Query("SELECT v FROM Crv v")
    Page<BaseEntity> findAll(PageRequest pageRequest);
}
