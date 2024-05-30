package com.diarpy.taskmanagementsystem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface CommentProjection {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long getId();
    String getText();
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty(value = "task_id")
    Long getTaskId();
    String getAuthor();
}
