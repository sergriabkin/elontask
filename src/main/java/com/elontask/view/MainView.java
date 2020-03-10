package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.List;

@Route
public class MainView extends VerticalLayout {
    private final TasksService service;
    private Grid<Task> grid;
    private TextField filter;

    public MainView(TasksService service) {
        this.service = service;
    }

    @PostConstruct
    private void init(){
        initGrid();
        initFilter();
        add(grid, filter);
    }

    private void initGrid() {
        grid = new Grid<>(Task.class);
        drawGrid("");
    }

    private void initFilter() {
        filter = new TextField();
        filter.setPlaceholder("Filter by title");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> drawGrid(e.getValue()));
    }

    private void drawGrid(String filterText) {
        grid.setItems(service.getDisplayedTasks(filterText));
    }

}
