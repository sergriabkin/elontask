package com.elontask.service;

import com.elontask.model.Task;

import java.util.List;

public interface TasksService {
    Task addTask(Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
//    List<Task> getTasksByTitle(String title);
    List<Task> getDisplayedTasks(String title, int maxPriority);
    Task editTask(Long id, Task task);
    void deleteTask(Long id);
}
