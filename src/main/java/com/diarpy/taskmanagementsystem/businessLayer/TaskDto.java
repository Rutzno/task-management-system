package com.diarpy.taskmanagementsystem.businessLayer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

public class TaskDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String author;
    private String assignee;
    @JsonProperty(value = "total_comments")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalComments;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.author = task.getAuthor().getEmail();
        this.assignee = task.getAssignee() == null ? "none" : task.getAssignee().getEmail();
        this.totalComments = task.getComments().size();
    }

    public TaskDto(Task task, Integer totalComments) {
        this(task);
        this.totalComments = totalComments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }
}
