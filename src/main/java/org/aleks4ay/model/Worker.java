package org.aleks4ay.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Worker implements BaseEntity<Worker>{

    private String id;
    private String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEntityName() {
        return "Worker";
    }

    @Override
    public String getDifferences(Worker worker) {
        String result = "";
        if (! this.id.equals(worker.id) ) {
            result += "id ['" + worker.id + "' --> '" + this.id + "'] ";
        }
        if (! this.name.equals(worker.name) ) {
            result += "name ['" + worker.name + "' --> '" + this.name + "'] ";
        }
        return result;
    }
}