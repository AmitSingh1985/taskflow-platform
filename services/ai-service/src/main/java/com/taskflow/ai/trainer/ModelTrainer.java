package com.taskflow.ai.trainer;

import java.io.File;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Component;

import com.taskflow.ai.model.TaskPriorityModel;
import com.taskflow.ai.training.TrainingDataGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelTrainer {

    private final TrainingDataGenerator trainingDataGenerator;

    private final TaskPriorityModel taskPriorityModel;

    public void train() {

        System.out.println(
                "Starting task priority model training..."
        );

        DataSet trainingData =
                trainingDataGenerator.generate(5000);

        ListDataSetIterator<DataSet> iterator =
                new ListDataSetIterator<>(
                        trainingData.asList(),
                        32
                );

        MultiLayerNetwork model =
                createModel();

        model.fit(iterator, 50);

        taskPriorityModel.setModel(model);

        saveModel(model);

        System.out.println(
                "Task priority model trained and saved successfully."
        );
    }

    private MultiLayerNetwork createModel() {

        MultiLayerConfiguration configuration =
                new NeuralNetConfiguration.Builder()
                        .seed(12345)
                        .updater(new Adam(0.001))
                        .list()

                        .layer(
                                new DenseLayer.Builder()
                                        .nIn(5)
                                        .nOut(16)
                                        .activation(Activation.RELU)
                                        .build()
                        )

                        .layer(
                                new DenseLayer.Builder()
                                        .nIn(16)
                                        .nOut(8)
                                        .activation(Activation.RELU)
                                        .build()
                        )

                        .layer(
                                new OutputLayer.Builder(
                                        LossFunctions.LossFunction.MCXENT
                                )
                                        .nIn(8)
                                        .nOut(3)
                                        .activation(Activation.SOFTMAX)
                                        .build()
                        )

                        .build();

        MultiLayerNetwork model =
                new MultiLayerNetwork(configuration);

        model.init();

        return model;
    }

    private void saveModel(MultiLayerNetwork model) {

        try {

            File modelFile =
                    new File(taskPriorityModel.getModelPath());

            File parentDirectory =
                    modelFile.getParentFile();

            if (parentDirectory != null) {
                parentDirectory.mkdirs();
            }

            ModelSerializer.writeModel(
                    model,
                    modelFile,
                    true
            );

            System.out.println(
                    "Model saved to: "
                            + modelFile.getAbsolutePath()
            );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Failed to save task priority model",
                    e
            );
        }
    }
}