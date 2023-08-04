package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.domain.BaseEntity;
import com.black.monkey.my.election.query.api.query.FindAllCrvQuery;
import com.black.monkey.my.election.query.api.query.FindAllNotesQuery;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.repository.CrvRepository;
import com.black.monkey.my.election.query.repository.NoteRepository;
import com.black.monkey.my.election.query.repository.VoteRegistrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryHandlerCrv implements QueryHandler {

    private final CrvRepository crvRepository;
    private final VoteRegistrationRepository voteRegistrationRepository;
    private final NoteRepository noteRepository;

    @Override
    public Page<BaseEntity> handle(FindCrvByIdQuery query) {
        /** FIXME  PRINTING ALL CRVS */
        Iterator<BaseEntity> it = crvRepository.findAll(PageRequest.of(0,1000)).iterator();
        while (it.hasNext()) {
            log.info("{}",it.next().toString());
        }

        Optional<Crv> optional = crvRepository.findById(query.getId());
        log.info("CRV {} found ? {}", query.getId(), optional.isPresent());
        return optional.isPresent() ?
                new PageImpl<>(List.of(optional.get()))
                : Page.empty();
    }

    @Override
    public Page<BaseEntity> handle(FindRegisteredVotesQuery query) {
        return voteRegistrationRepository.findByCrv(query.getCrvId(), PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.ASC, "voteNumber"));
    }

    @Override
    public Page<BaseEntity> handle(FindAllCrvQuery query) {
        return crvRepository.findAll(PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<BaseEntity> handle(FindAllNotesQuery query) {
        return noteRepository.findByCrvId(query.getCrvId(), PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.DESC, "timestamp"));
    }
}
