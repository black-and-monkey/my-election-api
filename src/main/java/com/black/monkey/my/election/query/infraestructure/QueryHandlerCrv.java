package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.repository.CrvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryHandlerCrv implements QueryHandler {

    private final CrvRepository crvRepository;

    @Override
    public List<BaseEntity> handle(FindCrvByIdQuery query) {
        Optional<Crv> optional = crvRepository.findById(query.getId());
        return optional.isPresent() ? List.of(optional.get()) : Collections.emptyList();
    }
}
