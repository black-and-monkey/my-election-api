package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, String> {

    @Query("SELECT x FROM Note x WHERE x.crv.id = ?1 ")
    Page<BaseEntity> findByCrvId(String crvId, PageRequest timestamp);
}
