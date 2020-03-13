package com.elontask.service;

import com.elontask.model.Step;
import com.elontask.model.Task;
import com.elontask.repository.StepRepository;
import com.elontask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {

    private final TaskRepository taskRepository;
    private final StepRepository stepRepository;

    public TasksServiceImpl(TaskRepository taskRepository, StepRepository stepRepository) {
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
        return taskRepository.findByTitleContainsIgnoreCase(title);
    }

    private List<Task> getTasksByMaxPriority(int priority) {
        if (priority == Task.MIN_PRIORITY){
            return getAllTasks();
        }
        return taskRepository.findByPriorityGreaterThanEqual(priority);
    }

    private List<Task> getListsIntersection(List<Task> firstList, List<Task> secondList) {
        return firstList.stream()
                .filter(secondList::contains)
                .collect(Collectors.toList());
    }

    @Override
    public Task editTask(Long id, Task task) {
        if (taskRepository.existsById(id)){
            deleteTask(id);
        }
        return addTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Step addStep(Step step) {
        return stepRepository.save(step);
    }

    @Override
    public Step editStep(Long id, Step step) {
        if (stepRepository.existsById(id)){
            deleteStep(id);
        }
        return addStep(step);
    }

    @Override
    public void deleteStep(Long id) {
        stepRepository.deleteById(id);
    }


}
