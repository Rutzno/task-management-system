package com.diarpy.taskmanagementsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.2
 */

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final MyUserRepository myUserRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, MyUserRepository myUserRepository) {
        this.taskRepository = taskRepository;
        this.myUserRepository = myUserRepository;
    }

    public Task save(@AuthenticationPrincipal UserDetails userDetails, Task newTask) {
        newTask.setMyUser(myUserRepository.findByEmail(userDetails.getUsername()));
        newTask.setAuthor(userDetails.getUsername());
        newTask.setStatus("CREATED");
        return taskRepository.save(newTask);
    }

    public List<Task> getTasks(String author) {
        Sort sortById = Sort.by("id").descending();
        return author == null ?
                taskRepository.findAll(sortById) :
                taskRepository.findByMyUser_Email(author, sortById);
    }
}
