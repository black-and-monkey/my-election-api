package com.black.monkey.my.election.cmd.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends CrudRepository<EventModel, String> {

    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);

    List<EventModel> findByAggregateIdentifierOrderByTimestampAsc(String aggregateIdentifier);
}
