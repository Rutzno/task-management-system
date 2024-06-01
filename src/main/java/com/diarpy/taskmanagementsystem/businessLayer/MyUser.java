package com.diarpy.taskmanagementsystem.businessLayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email invalid")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = ".{6,}", message = "Password must be at least 6 characters long")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private String authority;

    @OneToMany(mappedBy = "author")
    private List<Task> createdTasks = new ArrayList<>();
    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(List<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
}
