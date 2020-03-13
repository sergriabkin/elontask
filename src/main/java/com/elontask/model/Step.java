package com.elontask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Step(String instruction, Task task) {
        this.instruction = instruction;
        this.task = task;
    }

    @Override
    public String toString() {
        return task.getTitle() + " : " + instruction;
    }
}
