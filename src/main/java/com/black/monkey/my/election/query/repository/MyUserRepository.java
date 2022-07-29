package com.black.monkey.my.election.query.repository;

import com.black.monkey.my.election.query.domain.MyUser;
import org.springframework.data.repository.CrudRepository;

public interface MyUserRepository extends CrudRepository<MyUser, String> {
}
