package com.black.monkey.my.election.query.infraestructure;


import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.core.queries.BaseQuery;
import com.black.monkey.my.election.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}

