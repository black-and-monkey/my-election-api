package com.black.monkey.my.election.core.handlers;


import com.black.monkey.my.election.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
