package com.elontask;

import com.elontask.model.Task;
import com.elontask.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {
    @Bean
    public CommandLineRunner loadData(TaskRepository repository) {
        return args -> {
            repository.save(new Task(1, "wake up", "at 6:30", 5));
            repository.save(new Task(2, "take a brush and put a little makeup", "at 6:40", 4));
            repository.findAll().forEach(task -> log.info("task saved: " + task));
        };
    }
}