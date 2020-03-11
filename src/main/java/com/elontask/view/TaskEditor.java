package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.IntStream;

@SpringComponent
@UIScope
public class TaskEditor extends VerticalLayout implements KeyNotifier {

    private final TasksService service;

    private Task formTask;

    private Runnable onChangeStrategy;

    private Binder<Task> binder = new Binder<>(Task.class);

    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private Select<Integer> priority = new Select<>();
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    public TaskEditor(TasksService service) {
        this.service = service;
    }

    @PostConstruct
    protected void init() {
        initPriority();
        initActionButtons();
        add(title, description, priority, new HorizontalLayout(save, cancel, delete));
        binder.bindInstanceFields(this);
        addKeyPressListener(Key.ENTER, e -> save());
        setSpacing(true);
        setVisible(false);
    }

    private void initPriority() {
        priority.setLabel("Priority");
        priority.setItems(IntStream.rangeClosed(Task.MIN_PRIORITY, Task.MAX_PRIORITY).boxed());
    }

    private void initActionButtons() {
        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> save());

        cancel.addClickListener(e -> editTask(formTask));

        delete.getElement().getThemeList().add("error");
        delete.addClickListener(e -> delete());
    }

    protected void delete() {
        service.deleteTask(formTask.getId());
        onChangeStrategy.run();
    }

    protected void save() {
        service.addTask(updateFromFields(formTask));
        onChangeStrategy.run();
    }

    private Task updateFromFields(Task task) {
        task.setTitle(title.getValue());
        task.setDescription(description.getValue());
        task.setPriority(priority.getValue());
        return task;
    }

    public void editTask(Task task) {
        if (Objects.isNull(task)) {
            setVisible(false);
            return;
        }

        if (task.isPersisted()) {
            formTask = service.getTaskById(task.getId());
            cancel.setVisible(true);
        } else {
            formTask = task;
            cancel.setVisible(false);
        }

        binder.setBean(task);
        setVisible(true);
        title.focus();

    }

    public void setOnChangeStrategy(Runnable onChangeStrategy) {
        this.onChangeStrategy = onChangeStrategy;
    }

}
