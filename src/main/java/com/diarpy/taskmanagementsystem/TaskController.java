package com.diarpy.taskmanagementsystem;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.2
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task postTask(@AuthenticationPrincipal UserDetails userDetails,
                         @RequestBody @Valid Task task) {
        return taskService.save(userDetails, task);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String author) {
        return taskService.getTasks(author);
    }
}
