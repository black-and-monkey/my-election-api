package com.black.monkey.my.election.query.infraestructure;


import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.core.queries.BaseQuery;
import com.black.monkey.my.election.core.queries.QueryHandlerMethod;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> Page<U> send(BaseQuery query);
}

