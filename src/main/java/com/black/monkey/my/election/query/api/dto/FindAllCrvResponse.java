package com.black.monkey.my.election.query.api.dto;

import com.black.monkey.my.election.query.domain.Departamento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class FindAllCrvResponse {

    private int total;

    private List<FindAllCrvResponse.CrvLookupResponse> crvs;

    @Builder
    @Getter
    @Setter
    public static class CrvLookupResponse {

       private String id;
       private String description;
       private boolean isOpened;
       private Departamento departamento;
       private String locallidad;
       private String openBy;
       private LocalDateTime openTimestamp;
       private String closeBy;
       private LocalDateTime closeTimestamp;
   }
}
