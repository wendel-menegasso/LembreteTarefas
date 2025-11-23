package br.com.remembertask.business;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private String category;
    private String frequency;
    private String time;
    private String id;
    private String name;
    private String description;
    private String deleted;

    public Task() {

    }

    public Task(String category, String frequency, String time, String id, String name, String description, String deleted) {
        this.category = category;
        this.frequency = frequency;
        this.time = time;
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nTask{" +
                "category=" + category +
                ", frequency=" + frequency +
                ", time=" + time +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(category, task.category) && Objects.equals(frequency, task.frequency) && Objects.equals(time, task.time) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(deleted, task.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, frequency, time, id, name, description, deleted);
    }
}
