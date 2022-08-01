package com.black.monkey.my.election.query.infraestructure;


import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.core.queries.BaseQuery;
import com.black.monkey.my.election.core.queries.QueryHandlerMethod;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QueryDispatcherCrv implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> Page<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler was registered!");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send query to more than one handler!");
        }
        return handlers.get(0).handle(query);
    }
}
