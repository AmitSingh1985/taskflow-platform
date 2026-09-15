package com.taskflow.ai.runner;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.taskflow.ai.model.TaskPriorityModel;
import com.taskflow.ai.trainer.ModelTrainer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelTrainingRunner implements CommandLineRunner {

    private final ModelTrainer modelTrainer;

    private final TaskPriorityModel taskPriorityModel;

    @Override
    public void run(String... args) {

        File modelFile =
                new File(taskPriorityModel.getModelPath());

        if (modelFile.exists()) {

            System.out.println(
                    "Existing model found. "
                            + "Skipping training."
            );

            return;
        }

        System.out.println(
                "No existing model found. "
                        + "Training model..."
        );

        modelTrainer.train();
    }
}