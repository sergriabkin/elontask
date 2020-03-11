package com.elontask.view;

import com.elontask.model.Task;
import com.elontask.service.TasksService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SpringComponent
@UIScope
@Slf4j
public class TaskEditor extends VerticalLayout implements KeyNotifier {

    private final TasksService service;

    private Task formTask;

    private Runnable onChangeStrategy;

    private Binder<Task> binder = new Binder<>(Task.class);

    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    public TaskEditor(TasksService service) {
        this.service = service;
    }

    @PostConstruct
    protected void init() {
        add(title, description, initActionButtons());
        binder.bindInstanceFields(this);
        setSpacing(true);
        setVisible(false);
    }

    protected HorizontalLayout initActionButtons(){
        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> save());
        addKeyPressListener(Key.ENTER, e -> save());

        cancel.addClickListener(e -> editTask(formTask));

        delete.getElement().getThemeList().add("error");
        delete.addClickListener(e -> delete());

        return new HorizontalLayout(save, cancel, delete);
    }

    private void save(){
        formTask.setTitle(title.getValue());
        formTask.setDescription(description.getValue());
        service.addTask(formTask);
        onChangeStrategy.run();
    }

    private void delete(){
        service.deleteTask(formTask.getId());
        onChangeStrategy.run();
    }

    public void editTask(Task task){
        if (Objects.isNull(task)){
            setVisible(false);
            return;
        }

        if (task.isPersisted()){
            formTask = service.getTaskById(task.getId());
            cancel.setVisible(true);
        }
        else {
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
