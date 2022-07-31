package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindCrvByIdQuery query);

}
