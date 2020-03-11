package com.elontask.service;

import com.elontask.model.Task;
import com.elontask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Task> getDisplayedTasks(String title, int maxPriority) {
        if (title.isBlank() && maxPriority == Task.MIN_PRIORITY){
            return getAllTasks();
        }
        return getListsIntersection(getTasksByTitle(title), getTasksByMaxPriority(maxPriority));
    }

    private List<Task> getTasksByTitle(String title) {
        if (title.isBlank()){
            return getAllTasks();
        }
        return repository.findByTitleContainsIgnoreCase(title);
    }

    private List<Task> getTasksByMaxPriority(int priority) {
        if (priority == Task.MIN_PRIORITY){
            return getAllTasks();
        }
        return repository.findByPriorityGreaterThanEqual(priority);
    }

    private List<Task> getListsIntersection(List<Task> firstList, List<Task> secondList) {
        return firstList.stream()
                .filter(secondList::contains)
                .collect(Collectors.toList());
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
