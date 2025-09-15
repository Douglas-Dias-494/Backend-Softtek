package br.com.fiap.backend_Softtek.controllers;

import br.com.fiap.backend_Softtek.Models.TaskModel;
import br.com.fiap.backend_Softtek.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/daily")
    public ResponseEntity<List<TaskModel>> getDailyTasks(Principal principal) {
        String userId = principal.getName();
        List<TaskModel> tasks = taskService.getDailyTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/send-progress")
    public ResponseEntity<TaskModel> sendTaskProgress(@RequestBody Map<String, String> payload, Principal principal) {
        String userId = principal.getName();
        String taskName = payload.get("taskName");

        if (taskName == null || taskName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TaskModel updatedTasks = taskService.updateTaskStatus(userId, taskName);
        if (updatedTasks != null) {
            return ResponseEntity.ok(updatedTasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}