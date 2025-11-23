// TaskService.java
package br.com.remembertask.services;

import br.com.remembertask.business.WhatsAppSender;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import br.com.remembertask.ejb.TaskBean;
import br.com.remembertask.business.Task;

import java.io.IOException;

@Path("/tasks")
public class TaskService {

    @EJB
    private TaskBean taskBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createTask(Task task) throws IOException {
        return taskBean.saveTask(task);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadTasks() throws IOException, ClassNotFoundException {
        return taskBean.loadTasks();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String editTask(Task task) throws IOException, ClassNotFoundException {
        return taskBean.editTask(task);
    }
}