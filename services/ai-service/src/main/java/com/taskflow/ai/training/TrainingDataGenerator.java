package com.taskflow.ai.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

@Component
public class TrainingDataGenerator {

    private final Random random = new Random(12345);

    public DataSet generate(int numberOfSamples) {

        List<double[]> features = new ArrayList<>();
        List<double[]> labels = new ArrayList<>();

        for (int i = 0; i < numberOfSamples; i++) {

            double complexity = random.nextDouble(1, 11);
            double urgency = random.nextDouble(1, 11);
            double dependencies = random.nextInt(0, 6);
            double estimatedHours = random.nextDouble(1, 41);
            double descriptionLength = random.nextDouble(20, 500);

            String priority = determinePriority(
                    complexity,
                    urgency,
                    dependencies,
                    estimatedHours
            );

            features.add(new double[]{
                    complexity,
                    urgency,
                    dependencies,
                    estimatedHours,
                    descriptionLength
            });

            labels.add(createLabel(priority));
        }

        INDArray featureArray =
                Nd4j.create(features.toArray(new double[0][]));

        INDArray labelArray =
                Nd4j.create(labels.toArray(new double[0][]));

        return new DataSet(featureArray, labelArray);
    }

    private String determinePriority(
            double complexity,
            double urgency,
            double dependencies,
            double estimatedHours) {

        double score =
                complexity * 0.35
                + urgency * 0.40
                + dependencies * 0.10
                + estimatedHours * 0.15;

        if (score >= 7.5) {
            return "HIGH";
        }

        if (score >= 4.5) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private double[] createLabel(String priority) {

        return switch (priority) {

            case "LOW" ->
                    new double[]{1, 0, 0};

            case "MEDIUM" ->
                    new double[]{0, 1, 0};

            case "HIGH" ->
                    new double[]{0, 0, 1};

            default ->
                    throw new IllegalArgumentException(
                            "Unknown priority: " + priority
                    );
        };
    }
}