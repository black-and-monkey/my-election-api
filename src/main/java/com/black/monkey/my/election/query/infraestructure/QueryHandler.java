package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.api.query.FindAllCrvQuery;
import com.black.monkey.my.election.query.api.query.FindAllNotesQuery;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QueryHandler {
    Page<BaseEntity> handle(FindCrvByIdQuery query);
    Page<BaseEntity> handle(FindRegisteredVotesQuery query);


    Page<BaseEntity> handle(FindAllCrvQuery findAllCrvQuery);

    Page<BaseEntity> handle(FindAllNotesQuery findAllNotesQuery);
}
