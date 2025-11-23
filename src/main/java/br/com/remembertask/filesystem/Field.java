package br.com.remembertask.filesystem;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "size")
    private int size;

    @XmlElement(name = "order")
    private int order;

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
}
