package com.taskflow.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.ai.response.AiTaskDraft;
import com.taskflow.ai.response.AiTaskResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiTaskService {

    private final ChatClient.Builder chatClientBuilder;

    private final ObjectMapper objectMapper;

    public AiTaskDraft generateTask(String request) {

        ChatClient chatClient =
                chatClientBuilder.build();

        String response = chatClient
                .prompt()
                .system("""
                        You are a task management assistant.

                        Convert the user's request into a task.

                        Return ONLY valid JSON.

                        The JSON must contain exactly these fields:

                        {
                          "title": "short task title",
                          "description": "clear task description",
                          "priority": "LOW",
                          "assignee": "person name or null",
                          "dueDate": "YYYY-MM-DD or null",
                          "complexity": 1,
                          "urgency": 1,
                          "dependencyCount": 0,
                          "estimatedHours": 1
                        }

                        Priority must be exactly:
                        LOW, MEDIUM, or HIGH.

                        complexity:
                        - integer from 1 to 10
                        - estimate task complexity

                        urgency:
                        - integer from 1 to 10
                        - estimate task urgency

                        dependencyCount:
                        - non-negative integer
                        - estimate number of dependencies

                        estimatedHours:
                        - positive number
                        - estimate implementation time

                        If the user does not specify an assignee,
                        return null.

                        If the user does not specify a due date,
                        return null.

                        Do not add markdown.
                        Do not add ```json.
                        Do not add explanations.

                        Return only the JSON object.
                        """)
                .user(request)
                .call()
                .content();

        try {
            return objectMapper.readValue(
                    response,
                    AiTaskDraft.class
            );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "AI returned invalid task JSON: " + response,
                    e
            );
        }
    }
}