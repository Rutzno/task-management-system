package com.diarpy.taskmanagementsystem.presentation;

import com.diarpy.taskmanagementsystem.businessLayer.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDto postTask(Authentication authentication,
                            @RequestBody @Valid Task task) {
        return taskService.save(authentication, task);
    }

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(required = false) String author,
                                  @RequestParam(required = false) String assignee) {
        return taskService.findAllTasks(author, assignee);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<TaskDto> updateTaskByAssignee(Authentication authentication,
                                                        @PathVariable Long id,
                                                        @RequestBody AssignTaskRequest assignTaskRequest) {
        return taskService.updateByAssignee(authentication, id, assignTaskRequest.assignee());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDto> updateTaskByStatus(Authentication authentication,
                                                      @PathVariable Long id,
                                                      @RequestBody StatusTaskRequest statusTaskRequest) {
        return taskService.updateByStatus(authentication, id, statusTaskRequest.status());
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> commentTask(Authentication authentication,
                                         @PathVariable Long id,
                                         @RequestBody @Valid Comment comment) {
        return taskService.commentTask(authentication, id, comment);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentProjection>> getCommentsByTask(@PathVariable Long id) {
        return taskService.findAllCommentsByTask(id);
    }

    record AssignTaskRequest(String assignee) {}
    record StatusTaskRequest(TaskStatus status) {}
}
