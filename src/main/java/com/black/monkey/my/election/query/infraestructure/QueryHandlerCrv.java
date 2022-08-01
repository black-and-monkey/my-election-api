package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.repository.CrvRepository;
import com.black.monkey.my.election.query.repository.VoteRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryHandlerCrv implements QueryHandler {

    private final CrvRepository crvRepository;
    private final VoteRegistrationRepository voteRegistrationRepository;

    @Override
    public Page<BaseEntity> handle(FindCrvByIdQuery query) {
        Optional<Crv> optional = crvRepository.findById(query.getId());
        return optional.isPresent() ?
                new PageImpl(List.of(optional.get()))
                : Page.empty();
    }

    @Override
    public Page<BaseEntity> handle(FindRegisteredVotesQuery query) {
        return voteRegistrationRepository.findByCrv(query.getCrvId(), PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.ASC, "voteNumber"));
    }
}
