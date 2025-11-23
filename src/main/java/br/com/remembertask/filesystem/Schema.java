package br.com.remembertask.filesystem;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "schema")
@XmlAccessorType(XmlAccessType.FIELD)
public class Schema {

    @XmlElement(name = "field")
    private List<Field> fields;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
