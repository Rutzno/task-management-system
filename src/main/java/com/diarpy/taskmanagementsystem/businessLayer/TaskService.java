package com.diarpy.taskmanagementsystem.businessLayer;

import com.diarpy.taskmanagementsystem.persistance.CommentRepository;
import com.diarpy.taskmanagementsystem.persistance.MyUserRepository;
import com.diarpy.taskmanagementsystem.persistance.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final MyUserRepository myUserRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository, MyUserRepository myUserRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.myUserRepository = myUserRepository;
    }

    // Not Used
    public Task save(@AuthenticationPrincipal UserDetails userDetails, Task newTask) {
//        newTask.setMyUser(myUserRepository.findByEmail(userDetails.getUsername()));
        newTask.setAuthor(myUserRepository.findByEmail(userDetails.getUsername()));
        newTask.setStatus(TaskStatus.CREATED);
        return taskRepository.save(newTask);
    }

    public TaskDto save(Authentication auth, Task newTask) {
        newTask.setAuthor(myUserRepository.findByEmail(auth.getName()));
        newTask.setStatus(TaskStatus.CREATED);
//        newTask.setAssignee("none");
        newTask = taskRepository.save(newTask);
        return new TaskDto(newTask, null);
    }

    // Not Used
    public List<Task> findAllTasks(String author) {
        Sort sortById = Sort.by("id").descending();
        return author == null ?
                taskRepository.findAll(sortById) :
                taskRepository.findByAuthor_Email(author, sortById);
    }

    public List<TaskDto> findAllTasks(String authorEmail, String assigneeEmail) {
        Sort sortById = Sort.by("id").descending();
        Task probe = new Task();
        if (authorEmail != null) {
            MyUser author = myUserRepository.findByEmail(authorEmail.toLowerCase());
            if (author != null) {
                probe.setAuthor(author);
            } else {
                return new ArrayList<>(); // Return empty if author not found
            }
        }
        if (assigneeEmail != null) {
            MyUser assignee = myUserRepository.findByEmail(assigneeEmail.toLowerCase());
            if (assignee != null) {
                probe.setAssignee(assignee);
            } else {
                return new ArrayList<>(); // Return empty if assignee not found
            }
        }
        Example<Task> example = Example.of(probe);
        List<Task> tasks = taskRepository.findAll(example, sortById);
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto(task);
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    public ResponseEntity<TaskDto> updateByAssignee(Authentication authentication,
                                                    Long id,
                                                    String assignee) {
        if (!taskRepository.existsById(id) ||
                (!myUserRepository.existsByEmail(assignee) && !assignee.equals("none"))) {
            return ResponseEntity.notFound().build();
        }
        Optional<Task> optTask = taskRepository.findById(id);
        if (optTask.isPresent()) {
            Task task = optTask.get();
            if (!Objects.equals(authentication.getName(), task.getAuthor().getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            task.setAssignee(myUserRepository.findByEmail(assignee));
            taskRepository.save(task); // update task
            return ResponseEntity.ok(new TaskDto(task, null));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<TaskDto> updateByStatus(Authentication authentication,
                                                  Long id,
                                                  TaskStatus status) {
        // If the taskId path variable doesn't refer to an existing task ID, the endpoint respond with the 404 NOT FOUND.
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Task> optTask = taskRepository.findById(id);
        if (optTask.isPresent()) {
            Task task = optTask.get();
            // If the myUser trying to change the task status isn't the author or assignee, respond with the 403 FORBIDDEN.
            if (!Objects.equals(authentication.getName(), task.getAuthor().getEmail()) &&
                    !(task.getAssignee() != null && Objects.equals(authentication.getName(), task.getAssignee().getEmail()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            task.setStatus(status);
            taskRepository.save(task); // update task
            return ResponseEntity.ok(new TaskDto(task, null));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> commentTask(Authentication authentication,
                                         Long id,
                                         Comment newComment) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Task> optTask = taskRepository.findById(id);
        if (optTask.isPresent()) {
            Task task = optTask.get();
            newComment.setText(newComment.getText());
            newComment.setAuthor(authentication.getName());
            newComment.setTask(task);
            commentRepository.save(newComment);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<CommentProjection>> findAllCommentsByTask(Long id) {
        List<CommentProjection> results;
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Sort sortById = Sort.by("id").descending();
        results = commentRepository.findByTask_Id(id, sortById);
        return ResponseEntity.ok(results);
    }
}
