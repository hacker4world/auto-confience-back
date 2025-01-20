package com.group.autoconfienceback.dto.meeting_management;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AcceptOrRefuseMeetingDto {
    @NotEmpty(message = "Meeting id is required")
    private int meetingId;
    @NotEmpty(message = "Action is required")
    private boolean accepted;
}
