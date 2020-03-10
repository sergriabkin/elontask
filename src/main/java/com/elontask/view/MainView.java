package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route
public class MainView extends VerticalLayout {
    private final TasksService service;
    private final Grid<Task> grid;

    public MainView(TasksService service) {
        this.service = service;
        this.grid = new Grid<>(Task.class);
    }

    @PostConstruct
    private void init(){
        grid.setItems(service.getAllTasks());
        add(grid);
    }

}
