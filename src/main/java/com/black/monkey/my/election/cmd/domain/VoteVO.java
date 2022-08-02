package com.black.monkey.my.election.cmd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
public class VoteVO {

    private LocalDateTime timestamp;
    private LocalDate dob;
    private String fullName;
    private String ci;
    private String registerBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VoteVO vote = (VoteVO) o;

        return new EqualsBuilder().append(ci, vote.ci).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(ci).toHashCode();
    }
}
