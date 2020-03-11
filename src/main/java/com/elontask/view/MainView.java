package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Route
public class MainView extends VerticalLayout {

    private final TasksService service;
    private final TaskEditor editor;
    private Grid<Task> grid = new Grid<>(Task.class);
    private TextField titleFilter = new TextField();
    private Select<Integer> maxPriorityFilter = new Select<>();
    private Button newTaskButton = new Button("Add new task", VaadinIcon.PLUS.create());

    public MainView(TasksService service, TaskEditor editor) {
        this.service = service;
        this.editor = editor;
        setOnChangeTaskEditorStrategy();
    }

    @PostConstruct
    protected void init() {
        initGrid();
        initTitleFilter();
        initMaxPriorityFilter();
        initNewTaskButton();
        HorizontalLayout actionsLayout = new HorizontalLayout(maxPriorityFilter, titleFilter, newTaskButton);
        add(actionsLayout, grid, editor);
    }

    protected void initGrid() {
        grid.asSingleSelect().addValueChangeListener(e -> editor.editTask(e.getValue()));
        grid.setHeight("300px");
        grid.setColumns("id", "title", "description", "priority");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        drawGrid("", Task.MIN_PRIORITY);
    }

    protected void initTitleFilter() {
        titleFilter.setPlaceholder("Filter by title");
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        titleFilter.addValueChangeListener(e -> drawGrid(e.getValue(), maxPriorityFilter.getValue()));
    }

    protected void initMaxPriorityFilter() {
        maxPriorityFilter.setPlaceholder("Max priority");
        maxPriorityFilter.setItems(IntStream.rangeClosed(Task.MIN_PRIORITY, Task.MAX_PRIORITY).boxed());
        maxPriorityFilter.setValue(Task.MIN_PRIORITY);
        maxPriorityFilter.setMaxWidth("100px");
        maxPriorityFilter.addValueChangeListener(e -> drawGrid(titleFilter.getValue(), e.getValue()));
    }

    protected void initNewTaskButton() {
        newTaskButton.addClickListener(e -> editor.editTask(new Task("", "")));
    }

    private void drawGrid(String titleFilterText, int priorityFilterValue) {
        grid.setItems(service.getDisplayedTasks(titleFilterText, priorityFilterValue));
    }

    private void setOnChangeTaskEditorStrategy() {
        editor.setOnChangeStrategy(() -> {
            editor.setVisible(false);
            drawGrid(titleFilter.getValue(), maxPriorityFilter.getValue());
        });
    }

}
