package com.elontask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    public static final int DEFAULT_PRIORITY = 3;
    public static final int MIN_PRIORITY = 1;
    public static final int MAX_PRIORITY = 5;
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @Max(MAX_PRIORITY)
    @Min(MIN_PRIORITY)
    private int priority = DEFAULT_PRIORITY;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public boolean isPersisted(){
        return Objects.nonNull(id);
    }

}
