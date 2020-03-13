package com.elontask.service;

import com.elontask.model.Step;
import com.elontask.model.Task;

import java.util.List;

public interface TasksService {
    Task addTask(Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    List<Task> getDisplayedTasks(String title, int maxPriority);
    Task editTask(Long id, Task task);
    void deleteTask(Long id);

    Step addStep(Step step);
    Step editStep(Long id, Step step);
    void deleteStep(Long id);
}
