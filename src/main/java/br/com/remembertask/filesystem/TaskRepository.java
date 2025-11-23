package br.com.remembertask.filesystem;

import br.com.remembertask.business.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class TaskRepository {
    public static void saveTask(Task task) throws IOException {
        String index = readLastIndex();
        task.setId(index);
        String FILE = loadPath() + "/tasks-" + index + ".dat";
        Task taskReformated = reformatRegisterBeforeSave(task);
        File file = new File(FILE);
        boolean append = file.exists();
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = append
                     ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {

            oos.writeObject(taskReformated);
        }

    }

    private static String readLastIndex() throws IOException {
        String FILE = loadPath() + "/taskIndex.dat";
        File file = new File(FILE);
        if (!file.exists()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
                oos.writeObject("1");
                return "1";
            }
        };
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE)))) {
            String tmp = ois.readObject().toString();
            if (tmp != null) {
                int tmpInt = Integer.parseInt(tmp) + 1;
                File file1 = new File(FILE); // caminho do arquivo
                if (file1.delete()) {
                    System.out.println("Arquivo deletado com sucesso: " + file1.getName());
                } else {
                    System.out.println("Falha ao deletar o arquivo.");
                }
                saveLastIndex(String.valueOf(tmpInt));
                return String.valueOf(tmpInt);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "1";
    }

    private static void saveLastIndex(String value) throws FileNotFoundException {
        String FILE = loadPath() + "/taskIndex.dat";
        File file = new File(FILE);
        boolean append = file.exists();
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = append
                     ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {
            oos.writeObject(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Task> loadTasks() throws IOException, ClassNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File dir = new File(loadPath()); // diretório onde estão os arquivos

        if (!dir.exists() || !dir.isDirectory()) return tasks;

        // regex para validar nomes
        Pattern regex = Pattern.compile("tasks.*\\.dat");

        // percorre todos os arquivos do diretório
        for (File file : dir.listFiles()) {
            if (regex.matcher(file.getName()).matches()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    while (true) {
                        try {
                            Task task = (Task) ois.readObject();
                            Task taskReformated = getTask(task);
                            tasks.add(taskReformated);
                        } catch (EOFException eof) {
                            break; // fim do arquivo
                        }
                    }
                }
            }
        }
        return tasks;
    }

    public static Task editTask(Task task) throws IOException, ClassNotFoundException {
        String FILE = loadPath() + "/tasks-"+task.getId()+".dat";
        File file = new File(FILE);
        boolean append = file.exists();
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = append
                     ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {
            oos.writeObject(task);
            return task;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Task getTask(Task task) {
        String cat = task.getCategory().replace("$", "");
        String freq = task.getFrequency().replace("$", "");
        String time = task.getTime().replace("$", "");
        String name = task.getName().replace("$", "");
        String description = task.getDescription().replace("$", "");
        String id = task.getId().replace("$","");
        String deleted = task.getDeleted();
        Task taskReformated = new Task();
        taskReformated.setDescription(description);
        taskReformated.setTime(time);
        taskReformated.setName(name);
        taskReformated.setCategory(cat);
        taskReformated.setFrequency(freq);
        taskReformated.setDeleted(deleted);
        taskReformated.setId(id);
        return taskReformated;
    }

    private static Task reformatRegisterBeforeSave(Task task) {
        Schema schema = SchemaLoaderBean.getSchema();
        if (task.getCategory().length() < schema.getFields().get(0).getSize()) {
            int difference = schema.getFields().get(0).getSize() - task.getCategory().length();
            String builder = task.getCategory() +
                    "$".repeat(Math.max(0, difference));
            task.setCategory(builder);
        }
        if (task.getFrequency().length() < schema.getFields().get(1).getSize()) {
            int difference = schema.getFields().get(1).getSize() - task.getFrequency().length();
            String builder = task.getFrequency() +
                    "$".repeat(Math.max(0, difference));
            task.setFrequency(builder);
        }
        if (task.getTime().length() < schema.getFields().get(2).getSize()) {
            int difference = schema.getFields().get(2).getSize() - task.getTime().length();
            String builder = task.getTime() +
                    "$".repeat(Math.max(0, difference));
            task.setTime(builder);
        }
        if (task.getName().length() < schema.getFields().get(3).getSize()) {
            int difference = schema.getFields().get(3).getSize() - task.getName().length();
            String builder = task.getName() +
                    "$".repeat(Math.max(0, difference));
            task.setName(builder);
        }
        if (task.getDescription().length() < schema.getFields().get(4).getSize()) {
            int difference = schema.getFields().get(4).getSize() - task.getDescription().length();
            String builder = task.getDescription() +
                    "$".repeat(Math.max(0, difference));
            task.setDescription(builder);
        }
        if (task.getId().length() < schema.getFields().get(5).getSize()) {
            int difference = schema.getFields().get(5).getSize() - task.getId().length();
            String builder = task.getId() +
                    "$".repeat(Math.max(0, difference));
            task.setId(builder);
        }
        task.setDeleted("0");
        return task;
    }

    public static String loadPath() {
        Properties props = new Properties();

        try (InputStream input = TaskRepository.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Arquivo application.properties não encontrado em src/main/resources.");
                return "Error";
            }

            props.load(input);

            // Substitui ${DATABASE_PATH} pela variável de ambiente
            String rawValue = props.getProperty("file-system-path");
            if (rawValue != null && rawValue.contains("${DATABASE_PATH}")) {
                String envValue = System.getenv("DATABASE_PATH");
                if (envValue != null) {
                    props.setProperty("file-system-path", envValue);
                } else {
                    System.out.println("Variável de ambiente DATABASE_PATH não está definida.");
                }
            }

            // Exibe o valor final
            System.out.println("Valor da chave 'database': " + props.getProperty("file-system-path"));

        } catch (IOException e) {
            System.err.println("Erro ao ler application.properties: " + e.getMessage());
        }
        return props.getProperty("file-system-path");
    }
}
