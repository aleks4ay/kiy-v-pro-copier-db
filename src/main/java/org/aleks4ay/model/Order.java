package org.aleks4ay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aleks4ay.tools.DateConverter;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Order implements BaseEntity<Order>{

    private String idDoc;
    private String clientId;
    private String managerId;
    private int durationTime;
    private Timestamp dateToFactory;
    private double price;

    @Override
    public String getEntityName() {
        return "Order";
    }

    @Override
    public String getId() {
        return idDoc;
    }

    @Override
    public String getDifferences(Order order) {
        String result = "";
        if (! this.idDoc.equals(order.idDoc) ) {
            result += "idDoc [" + order.idDoc + "--> " + this.idDoc + "] ";
        }
        if (! this.clientId.equals(order.clientId) ) {
            result += "idClient [" + order.clientId + "--> " + this.clientId + "] ";
        }
        if (! this.managerId.equals(order.managerId) ) {
            result += "idManager [" + order.managerId + "--> " + this.managerId + "] ";
        }
        if (this.durationTime != order.durationTime) {
            result += "durationTime [" + order.durationTime + "--> " + this.durationTime + "] ";
        }
        if (this.dateToFactory != null & order.dateToFactory != null
                && this.dateToFactory.getTime() != order.dateToFactory.getTime()) {
            result += "dateToFactory [" + DateConverter.getStrFromTimestamp(order.dateToFactory) + "--> "
                    + DateConverter.getStrFromTimestamp(this.dateToFactory) + "] ";
        }
        if (this.price != order.price) {
            result += "price [" + order.price + "--> " + this.price + "] ";
        }
        return result;
    }
}
