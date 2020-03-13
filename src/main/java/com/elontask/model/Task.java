package com.elontask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    public static final int DEFAULT_PRIORITY = 3;
    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Max(MAX_PRIORITY)
    @Min(MIN_PRIORITY)
    @Column
    private int priority = DEFAULT_PRIORITY;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Step> steps = new ArrayList<>();

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(String title, String description, @Max(MAX_PRIORITY) @Min(MIN_PRIORITY) int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public boolean isPersisted(){
        return Objects.nonNull(id);
    }

}
