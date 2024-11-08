package com.example.to_do_list.service;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.to_do_list.model.Task;
import com.example.to_do_list.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new RuntimeException("NICE TASK NOT FOUND MY BOY" + id);

        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task updTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updTask.getTitle());
        existingTask.setDescription(updTask.getDescription());
        existingTask.setCompleted(updTask.isCompleted());
        return taskRepository.save(updTask);
    }

}
