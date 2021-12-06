package org.aleks4ay.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Embodiment implements BaseEntity<Embodiment>{

    private String id;
    private String description;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEntityName() {
        return "Embodiment";
    }

    @Override
    public String getDifferences(Embodiment embodiment) {
        String result = "";
        if (! this.id.equals(embodiment.id) ) {
            result += "id ['" + embodiment.id + "' --> '" + this.id + "'] ";
        }
        if (! this.description.equals(embodiment.description) ) {
            result += "name ['" + embodiment.description + "' --> '" + this.description + "'] ";
        }
        return result;
    }
}