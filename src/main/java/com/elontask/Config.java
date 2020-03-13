package com.elontask;

import com.elontask.model.Step;
import com.elontask.model.Task;
import com.elontask.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class Config {
    @Bean
    public CommandLineRunner loadData(TaskRepository repository) {
        return args -> {
            Task wakingUp = new Task(1L, "wake up", "at 6:30", 5);
            repository.save(wakingUp);

            Task brushingTeeth = new Task(2L, "take a brush and put a little makeup", "at 6:40", 4);
            repository.save(brushingTeeth);

            Task morningExercises = new Task(3L, "do some morning exercises", "from 6:50, at least 30 minutes", 4);
            Step jumps = new Step("jumps", morningExercises);
            Step warmUp = new Step("warm-up", morningExercises);
            Step stretching = new Step("stretching", morningExercises);
            morningExercises.setSteps(List.of(jumps, warmUp, stretching));
            repository.save(morningExercises);

            repository.findAll().forEach(task -> log.info("task saved: " + task));
        };
    }
}