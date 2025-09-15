package br.com.fiap.backend_Softtek.service;

import br.com.fiap.backend_Softtek.Models.TaskModel;
import br.com.fiap.backend_Softtek.Models.TaskStatusModel;
import br.com.fiap.backend_Softtek.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Tarefas padrão para cada dia
    private List<TaskStatusModel> defaultTasks() {
        List<TaskStatusModel> tasks = new ArrayList<>();
        tasks.add(new TaskStatusModel("Acalmar a respiração", "5 minutos de respiração profunda.", false));
        tasks.add(new TaskStatusModel("Alongamento Diário", "Dê uma pausa para se alongar.", false));
        tasks.add(new TaskStatusModel("O Sol faz bem!", "15 minutos de sol. Uma dose de sol pode ajudar muito no seu humor.", false));
        tasks.add(new TaskStatusModel("Reflexão", "Escreva em um papel 3 coisas pelas quais foi grato(a) no dia de hoje.", false));
        return tasks;
    }

    public List<TaskModel> getDailyTasks(String userId) {
        LocalDate today = LocalDate.now();

        // Altere a busca no repositório para retornar uma lista
        List<TaskModel> existingTasks = taskRepository.findByUserIdAndDate(userId, today);

        // Se a lista de tarefas para hoje NÃO estiver vazia, retorne-a
        if (!existingTasks.isEmpty()) {
            return existingTasks;
        } else {
            // Se a lista estiver vazia, crie as tarefas padrão para o dia
            List<TaskModel> newTasks = new ArrayList<>();
            List<TaskStatusModel> defaultTaskModels = defaultTasks();

            for (TaskStatusModel defaultTask : defaultTaskModels) {
                TaskModel newTaskModel = new TaskModel();
                newTaskModel.setUserId(userId);
                newTaskModel.setDate(today.toString());

                // CORREÇÃO FINAL: Mapeando os campos do TaskStatusModel
                newTaskModel.setTaskName(defaultTask.getName());
                newTaskModel.setIsDone(defaultTask.isCompleted());
                newTaskModel.setStatus("pending");

                newTasks.add(newTaskModel);
            }

            return taskRepository.saveAll(newTasks);
        }
    }

    public TaskModel updateTaskStatus(String userId, String taskName) {
        // 1. Tente encontrar a tarefa para o usuário
        Optional<TaskModel> optionalTask = taskRepository.findByUserIdAndTaskName(userId, taskName);

        if (optionalTask.isPresent()) {
            // 2. Se a tarefa existir, atualize o status
            TaskModel task = optionalTask.get();
            task.setStatus("completed"); // Exemplo
            return taskRepository.save(task);
        } else {
            // 3. Se a tarefa NÃO existir, crie uma nova
            TaskModel newTask = new TaskModel();
            newTask.setUserId(userId);
            newTask.setTaskName(taskName);
            newTask.setStatus("completed"); // Define o status inicial
            // Adicione outros campos necessários aqui

            return taskRepository.save(newTask);
        }
    }
}
