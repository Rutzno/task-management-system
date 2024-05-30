package com.diarpy.taskmanagementsystem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.4
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task postTask(Authentication authentication,
                         @RequestBody @Valid Task task) {
        return taskService.save(authentication, task);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String author,
                               @RequestParam(required = false) String assignee) {
        return taskService.findAllTasks(author, assignee);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Task> updateTaskByAssignee(Authentication authentication,
                                                     @PathVariable Long id,
                                                     @RequestBody AssignTaskRequest assignTaskRequest) {
        return taskService.updateByAssignee(authentication, id, assignTaskRequest.assignee());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskByStatus(Authentication authentication,
                                                   @PathVariable Long id,
                                                   @RequestBody StatusTaskRequest statusTaskRequest) {
        return taskService.updateByStatus(authentication, id, statusTaskRequest.status());
    }

    record AssignTaskRequest(String assignee) {}
    record StatusTaskRequest(TaskStatus status) {}
}