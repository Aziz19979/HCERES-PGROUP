package org.centrale.hceres.dto.csv;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class ImportCsvMetric implements Serializable {

    // *************** Parsing CSV ***************
    private int totalInCsv;

    private int totalLineErrors;
    // a line can have multiple errors
    private int totalErrors;

    // line with same merging keys are overwritten
    private int totalDuplicatesInCsv;
    private List<List<Integer>> duplicateLines;


    // *************** INSERTION ***************
    private int totalInDatabase;
    // the records that are already in the database and skipped
    private int totalMergedWithDatabase;
    private int totalInserted;

    public ImportCsvMetric() {
        this.duplicateLines = new ArrayList<>();
    }
}
