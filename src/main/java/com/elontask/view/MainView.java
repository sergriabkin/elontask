package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route
public class MainView extends VerticalLayout {

    private final TasksService service;
    private final TaskEditor editor;
    private Grid<Task> grid = new Grid<>(Task.class);
    private TextField filter = new TextField();
    private Button newTaskButton = new Button("Add new task", VaadinIcon.PLUS.create());

    public MainView(TasksService service, TaskEditor editor) {
        this.service = service;
        this.editor = editor;
        setOnChangeTaskEditorStrategy(filter);
    }

    @PostConstruct
    protected void init() {
        initGrid();
        initFilter();
        initNewTaskButton();
        HorizontalLayout actionsLayout = new HorizontalLayout(filter, newTaskButton);
        add(actionsLayout, grid, editor);
    }

    protected void initGrid() {
        grid.asSingleSelect().addValueChangeListener(e -> editor.editTask(e.getValue()));
        grid.setHeight("300px");
        grid.setColumns("id", "title", "description", "priority");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        drawGrid("");
    }

    protected void initFilter() {
        filter.setPlaceholder("Filter by title");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> drawGrid(e.getValue()));
    }

    protected void initNewTaskButton() {
        newTaskButton.addClickListener(e -> editor.editTask(new Task("", "")));
    }

    private void drawGrid(String filterText) {
        grid.setItems(service.getDisplayedTasks(filterText));
    }

    private void setOnChangeTaskEditorStrategy(TextField filter) {
        editor.setOnChangeStrategy(() -> {
            editor.setVisible(false);
            drawGrid(filter.getValue());
        });
    }

}
