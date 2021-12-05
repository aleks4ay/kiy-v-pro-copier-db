package org.aleks4ay.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client implements BaseEntity<Client>{

    private String id;
    private String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEntityName() {
        return "Client";
    }

    @Override
    public String getDifferences(Client client) {
        String result = "";
        if (! this.id.equals(client.id) ) {
            result += "id ['" + client.id + "' --> '" + this.id + "'] ";
        }
        if (! this.name.equals(client.name) ) {
            result += "name ['" + client.name + "' --> '" + this.name + "'] ";
        }
        return result;
    }
}
