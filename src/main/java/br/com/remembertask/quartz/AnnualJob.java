package br.com.remembertask.quartz;

import br.com.remembertask.business.Task;
import br.com.remembertask.business.WhatsAppSender;
import br.com.remembertask.ejb.TaskBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class AnnualJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        TaskBean taskBean = new TaskBean();
        try {
            String tasks = taskBean.loadTasks();
            Type listType = new TypeToken<List<Task>>(){}.getType();
            Gson gson = new Gson();
            List<Task> taskList = gson.fromJson(tasks, listType);
            StringBuilder resp = new StringBuilder();
            resp.append("Tarefas anuais: \n\n" );
            for (Task task : taskList) {
                if (task.getFrequency().equals("ANUAIS")) {
                    resp.append("Nome da tarefa: ").append(task.getName()).append("\n");
                    resp.append("Descrição: ").append(task.getDescription()).append("\n");
                    resp.append("Categoria: ").append(task.getCategory()).append("\n");
                    resp.append("Tempo: ").append(task.getTime()).append("\n");
                }
            }
            WhatsAppSender whatsAppSender = new WhatsAppSender();
            whatsAppSender.sendMessage(resp.toString());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
