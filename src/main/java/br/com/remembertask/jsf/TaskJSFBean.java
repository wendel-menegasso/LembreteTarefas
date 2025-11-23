package br.com.remembertask.jsf;

import br.com.remembertask.business.Task;
import br.com.remembertask.filesystem.TaskRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class TaskJSFBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String time;
    private String frequency;
    private String category;
    private String result;
    private String deleted;
    private String id;

    private List<Task> items = new ArrayList<>();

    public List<Task> getItems() {
        return items;
    }

    public String sendTask() {
        int responseCode = 0;
        try {
            URL url = new URL("http://127.0.0.1:8080/lembretetarefas/api/tasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Task task = new Task();
            task.setName(name);
            task.setDescription(description);
            task.setTime(time);
            task.setFrequency(frequency);
            task.setCategory(category);

            Gson gson = new Gson();
            String json = gson.toJson(task);
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            responseCode = conn.getResponseCode();
            System.out.println("Resposta: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(responseCode);
    }

    public String sendMessage() {
        int responseCode = 0;
        try {
            URL url = new URL("http://127.0.0.1:8080/lembretetarefas/api/edittasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            responseCode = conn.getResponseCode();
            conn.disconnect();

            System.out.println("Resposta: " + responseCode);
            result = String.valueOf(responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(responseCode);
    }

    public void deleteTask(Task task) {
        String index = task.getId();
        String FILE = TaskRepository.loadPath() + "/tasks-"+index+".dat";
        File file = new File(FILE); // caminho do arquivo

        if (file.delete()) {
            System.out.println("Arquivo deletado com sucesso: " + file.getName());
        } else {
            System.out.println("Falha ao deletar o arquivo.");
        }
    }

    private List<Task> filteredTaskList = new ArrayList<>();

    public List<Task> getFilteredTaskList() {
        return filteredTaskList;
    }

    public void setFilteredTaskList(List<Task> filteredTaskList) {
        this.filteredTaskList = filteredTaskList;
    }

    public void findTasks() {
        if (filteredTaskList != null && !filteredTaskList.isEmpty()) {
            items = filteredTaskList;
        }
    }

    public void viewTask() {
        int responseCode = 0;
        try {
            URL url = new URL("http://127.0.0.1:8080/lembretetarefas/api/tasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            responseCode = conn.getResponseCode();
            InputStreamReader reader;

            // Se for sucesso (200–299), usa getInputStream; senão usa getErrorStream
            if (responseCode >= 200 && responseCode < 300) {
                reader = new InputStreamReader(conn.getInputStream());
            } else {
                reader = new InputStreamReader(conn.getErrorStream());
                return;
            }

            BufferedReader in = new BufferedReader(reader);
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            in.close();
            conn.disconnect();

            Gson gson = new Gson();
            System.out.println(response);
            Type listType = new TypeToken<List<Task>>(){}.getType();
            List<Task> taskList = gson.fromJson(response.toString(), listType);
            this.items.clear();
            for (Task task : taskList) {
                if (!task.getDeleted().equals("0")) continue;
                this.items.add(task);
            }
            System.out.println("Resposta: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarPagina() {
        viewTask();
    }

    public void onTabChange(TabChangeEvent event) {
        int index = event.getIndex();
        if (index > 0) { // segunda aba
            carregarPagina();
        }
    }

    public void onRowEdit(RowEditEvent<Task> event) {
        Task oldTask = event.getObject();
        deleteTask(oldTask);
        int responseCode = 0;
        try {
            URL url = new URL("http://127.0.0.1:8080/lembretetarefas/api/tasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(oldTask);
            System.out.println(json);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            responseCode = conn.getResponseCode();
            System.out.println("Resposta: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Task> selectedTasks;

    public List<Task> getSelectedTasks() {
        return selectedTasks;
    }


    public void onRowCancel(RowEditEvent<Task> event) {
        System.out.println("Edição cancelada para: " + event.getObject());
    }

    public void setSelectedTasks(List<Task> selectedTasks) {
        this.selectedTasks = selectedTasks;
        deleteSelectedTasks();
    }

    public void deleteSelectedTasks() {
        if (selectedTasks != null && !selectedTasks.isEmpty()) {
            for (Task task : selectedTasks) {
                deleteTask(task); // já existe no seu bean
            }
            // Atualiza a lista
            viewTask();
            System.out.println("Tarefas excluídas: " + selectedTasks.size());
        }
    }



    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
