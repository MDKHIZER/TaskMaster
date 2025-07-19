package com.taskmanager.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.application.dto.TaskDto;
import com.taskmanager.application.entity.Task;
import com.taskmanager.application.repository.TaskRepository;
import com.taskmanager.application.repository.UserRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskDto> getAllTasks(String username) {
        return taskRepository.findByAssignedToUsername(username).stream().map(this::convertToDto).toList();
    }

    public TaskDto createTask(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        userRepository.findByUsername(dto.getAssignedTo()).ifPresent(task::setAssignedTo);
        taskRepository.save(task);
        return convertToDto(task);
    }

    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        userRepository.findByUsername(dto.getAssignedTo()).ifPresent(task::setAssignedTo);
        taskRepository.save(task);
        return convertToDto(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDto convertToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setAssignedTo(task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : null);
        return dto;
    }
}
