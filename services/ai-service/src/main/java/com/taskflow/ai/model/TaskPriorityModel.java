package com.taskflow.ai.model;

import java.io.File;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Getter
@RequiredArgsConstructor
public class TaskPriorityModel {

    @Value("${taskflow.ai.model-path}")
    private String modelPath;

    private MultiLayerNetwork model;

    @PostConstruct
    public void initialize() {

        File modelFile = new File(modelPath);

        if (modelFile.exists()) {

            System.out.println(
                    "Loading trained model from: "
                            + modelFile.getAbsolutePath()
            );

            try {

                model = ModelSerializer.restoreMultiLayerNetwork(
                        modelFile
                );

                System.out.println(
                        "Task priority model loaded successfully."
                );

            } catch (Exception e) {

                throw new IllegalStateException(
                        "Failed to load task priority model",
                        e
                );
            }

        } else {

            System.out.println(
                    "No trained model found. "
                    + "Model must be trained before prediction."
            );
        }
    }

    public void setModel(MultiLayerNetwork model) {
        this.model = model;
    }
}