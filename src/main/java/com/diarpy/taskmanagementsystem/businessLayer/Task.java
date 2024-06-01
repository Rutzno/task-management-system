package com.diarpy.taskmanagementsystem.businessLayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private MyUser author;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private MyUser assignee;
    @OneToMany(mappedBy = "task")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Comment> comments = new ArrayList<>();

    public Task() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public MyUser getAssignee() {
        return assignee;
    }

    public void setAssignee(MyUser assignee) {
        this.assignee = assignee;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}