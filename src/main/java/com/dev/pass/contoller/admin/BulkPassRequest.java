package com.dev.pass.contoller.admin;

import com.dev.pass.util.LocalDateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BulkPassRequest {
    private Integer packageSeq;
    private String userGroupId;
    private LocalDateTime startedAt;

    public void setStartedAt(String startedAtString) {
        // String to LocalDateTime
        this.startedAt = LocalDateTimeUtils.parse(startedAtString);
    }

}
