package com.black.monkey.my.election.core.queries;



import com.black.monkey.my.election.core.domain.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    Page<BaseEntity> handle(T query);
}
