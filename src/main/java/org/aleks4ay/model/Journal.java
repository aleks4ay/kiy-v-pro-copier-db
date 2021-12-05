package org.aleks4ay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aleks4ay.tools.DateConverter;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Journal implements BaseEntity<Journal> {

    private String idDoc;
    private String docNumber;
    private Timestamp dateCreate;

    @Override
    public String getDifferences(Journal journal) {
        String result = "";
        if (! this.idDoc.equals(journal.idDoc) ) {
            result += "idDoc ['" + journal.idDoc + "' --> '" + this.idDoc + "'] ";
        }
        if (! this.docNumber.equals(journal.docNumber) ) {
            result += "doc Number ['" + journal.docNumber + "' --> '" + this.docNumber + "'] ";
        }
        if (this.dateCreate.getTime() != journal.dateCreate.getTime()) {
            result += "dateToFactory ['" + DateConverter.getStrFromTimestamp(journal.dateCreate) + "' --> '"
                    + DateConverter.getStrFromTimestamp(this.dateCreate) + "'] ";
        }
        return result;
    }

    @Override
    public String getId() {
        return idDoc;
    }

    @Override
    public String getEntityName() {
        return "Journal";
    }
}
