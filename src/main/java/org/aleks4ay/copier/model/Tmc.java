package org.aleks4ay.copier.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tmc implements BaseEntity<Tmc> {

    private String id;
    private String idParent;
    private String code;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private String descr;
    private int isFolder;
    private String descrAll;
    private String type;

    public Tmc(String id) {
        this.id = id;
    }

    @Override
    public String getEntityName() {
        return "Tmc";
    }

    public boolean equalsTechno(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tmc tmc = (Tmc) o;

        if (sizeA != tmc.sizeA) return false;
        if (sizeB != tmc.sizeB) return false;
        if (sizeC != tmc.sizeC) return false;
        if (id != null ? !id.equals(tmc.id) : tmc.id != null) return false;
        if (idParent != null ? !idParent.equals(tmc.idParent) : tmc.idParent != null) return false;
        return descr != null ? descr.equals(tmc.descr) : tmc.descr == null;
    }

    @Override
    public String getDifferences(Tmc tmc) {
        String result = "";
        if (! this.id.equals(tmc.id)) {
            result += "id ['" + tmc.id + "' --> '" + this.id + "'] ";
        }
        if (! this.idParent.equals(tmc.idParent) ) {
            result += "idParent ['" + tmc.idParent + "' --> '" + this.idParent + "'] ";
        }
        if (! this.code.equals(tmc.code) ) {
            result += "code ['" + tmc.code + "' --> '" + this.code + "'] ";
        }
        if (this.sizeA != tmc.sizeA) {
            result += "sizeA ['" + tmc.sizeA + "' --> '" + this.sizeA + "'] ";
        }
        if (this.sizeB != tmc.sizeB) {
            result += "sizeB ['" + tmc.sizeB + "' --> '" + this.sizeB + "'] ";
        }
        if (this.sizeC != tmc.sizeC) {
            result += "sizeC ['" + tmc.sizeC + "' --> '" + this.sizeC + "'] ";
        }
        if (this.isFolder != tmc.isFolder) {
            result += "isFolder ['" + tmc.isFolder + "' --> '" + this.isFolder + "'] ";
        }
        if (! this.descr.equals(tmc.descr) ) {
            result += "descr ['" + tmc.descr + "' --> '" + this.descr + "'] ";
        }
        if (! this.descrAll.equals(tmc.descrAll) ) {
            result += "descrAll ['" + tmc.descrAll + "' --> '" + this.descrAll + "'] ";
        }
        if (! this.type.equals(tmc.type) ) {
            result += "type ['" + tmc.type + "' --> '" + this.type + "'] ";
        }
        return result;
    }
}
