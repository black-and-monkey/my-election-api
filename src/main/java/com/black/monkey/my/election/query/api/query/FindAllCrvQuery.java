package com.black.monkey.my.election.query.api.query;

import com.black.monkey.my.election.core.queries.BaseQuery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindAllCrvQuery extends BaseQuery {

    private int page;
    private int size;
}
