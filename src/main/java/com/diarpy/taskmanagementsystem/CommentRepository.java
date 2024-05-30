package com.diarpy.taskmanagementsystem;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.5
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c.id AS id, c.text AS text, c.task.id AS taskId, c.author AS author " +
            "FROM Comment c WHERE c.task.id = :id")
    List<CommentProjection> findByTask_Id(@Param("id") Long id, Sort sort);
}