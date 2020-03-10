package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.repository.TaskRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route
public class MainView extends VerticalLayout {
    private final TaskRepository repository;
    private final Grid<Task> grid;

    public MainView(TaskRepository repository) {
        this.repository = repository;
        this.grid = new Grid<>(Task.class);
    }

    @PostConstruct
    private void init(){
        grid.setItems(repository.findAll());
        add(grid);
    }

}
