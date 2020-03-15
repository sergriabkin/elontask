package com.elontask.view;

import com.elontask.model.Step;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@UIScope
public class StepsPanel extends VerticalLayout {

    private Span title = new Span("Steps to do:");
    private Grid<Step> grid = new Grid<>(Step.class);

    @PostConstruct
    protected void init() {
        initGrid();
        add(title, grid);
    }

    protected void initGrid() {
        grid.setHeight("300px");
        grid.setColumns("id", "instruction");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
    }


    public void drawSteps(List<Step> steps) {
        if (steps.isEmpty()){
            setVisible(false);
        }
        else {
            setVisible(true);
            grid.setItems(steps);
        }
    }

}
