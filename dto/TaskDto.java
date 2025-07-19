package com.taskmanager.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String assignedTo;
    // getters and setters
}
