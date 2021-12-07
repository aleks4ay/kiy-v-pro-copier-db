package org.aleks4ay.copier.model;

import lombok.Data;

@Data
//@AllArgsConstructor
public class Description implements BaseEntity<Description> {

    private String id;
    private String idDoc;
    private int position;
    private String idTmc;
    private int quantity;
    private String descrSecond;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private String embodiment;
//    private Status status;


    public Description(String id, String idDoc, int position, String idTmc, int quantity,
                       String descrSecond, int sizeA, int sizeB, int sizeC, String embodiment) {
        this.id = id;
        this.idDoc = idDoc;
        this.position = position;
        this.idTmc = idTmc;
        this.quantity = quantity;
        this.descrSecond = descrSecond;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.embodiment = embodiment;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    @Override
    public String getDifferences(Description description) {
        String result = "";
        if (! this.id.equals(description.id)) {
            result += "id ['" + description.id + "' --> '" + this.id + "'] ";
        }
        if (! this.idDoc.equals(description.idDoc) ) {
            result += "idDoc ['" + description.idDoc + "' --> '" + this.idDoc + "'] ";
        }
        if (this.position != description.position) {
            result += "position ['" + description.position + "' --> '" + this.position + "'] ";
        }
        if (! this.idTmc.equals(description.idTmc) ) {
            result += "idTmc ['" + description.idTmc + "' --> '" + this.idTmc + "'] ";
        }
        if (this.quantity != description.quantity) {
            result += "quantity ['" + description.quantity + "' --> '" + this.quantity + "'] ";
        }
        if (! this.descrSecond.equals(description.descrSecond) ) {
            result += "descrSecond ['" + description.descrSecond + "' --> '" + this.descrSecond + "'] ";
        }
        if (this.sizeA != description.sizeA) {
            result += "sizeA ['" + description.sizeA + "' --> '" + this.sizeA + "'] ";
        }
        if (this.sizeB != description.sizeB) {
            result += "sizeB ['" + description.sizeB + "' --> '" + this.sizeB + "'] ";
        }
        if (this.sizeC != description.sizeC) {
            result += "sizeC ['" + description.sizeC + "' --> '" + this.sizeC + "'] ";
        }
        if (! this.embodiment.equals(description.embodiment) ) {
            result += "embodiment ['" + description.embodiment + "' --> '" + this.embodiment + "'] ";
        }
        return result;
    }
}
