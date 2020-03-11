package com.elontask.repository;

import com.elontask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitleContainsIgnoreCase(String title);
    List<Task> findByPriorityGreaterThanEqual(int priority);
}
