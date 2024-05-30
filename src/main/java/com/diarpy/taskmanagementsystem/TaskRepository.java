package com.diarpy.taskmanagementsystem;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll(Sort sort);
    List<Task> findByMyUser_Email(String email, Sort sort);
    //    boolean existsById(Long id);
     /*@Query("SELECT DISTINCT t, t.comments.size AS total_comments FROM Task t LEFT JOIN FETCH t.comments")
    List<Task> findAllByAssigneeAndAuthor(String assignee, String author);*/
}
