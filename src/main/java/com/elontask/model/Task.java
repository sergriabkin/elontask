package com.elontask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private int priority = 3;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public boolean isPersisted(){
        return Objects.nonNull(id);
    }

}
