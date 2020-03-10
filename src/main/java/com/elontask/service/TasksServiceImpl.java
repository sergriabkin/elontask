package com.elontask.service;

import com.elontask.model.Task;
import com.elontask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    private final TaskRepository repository;

    public TasksServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task addTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> getTasksByTitle(String title) {
        return repository.findByTitleContainsIgnoreCase(title);
    }

    @Override
    public List<Task> getDisplayedTasks(String title) {
        if (title.isBlank()){
            return getAllTasks();
        }
        return getTasksByTitle(title);
    }

    @Override
    public Task editTask(Long id, Task task) {
        if (repository.existsById(id)){
            deleteTask(id);
        }
        return addTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
