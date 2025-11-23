package br.com.remembertask.ejb;

import br.com.remembertask.filesystem.TaskRepository;
import com.google.gson.Gson;
import jakarta.ejb.Stateless;
import br.com.remembertask.business.Task;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class TaskBean {
    public String saveTask(Task task) throws IOException {
        TaskRepository.saveTask(task);
        return "Tarefa salva com sucesso!";
    }

    public String loadTasks() throws IOException, ClassNotFoundException {
        List<Task> tasks = TaskRepository.loadTasks();
        Gson gson = new Gson();
        return gson.toJson(tasks);
    }

    public String editTask(Task task) throws IOException, ClassNotFoundException {
        Task updatedTask = TaskRepository.editTask(task);
        Gson gson = new Gson();
        String json = gson.toJson(updatedTask);
        System.out.println(json);
        return String.format(json);
    }
}
