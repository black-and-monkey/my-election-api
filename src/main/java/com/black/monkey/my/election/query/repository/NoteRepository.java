package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.query.domain.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, String> {
}
