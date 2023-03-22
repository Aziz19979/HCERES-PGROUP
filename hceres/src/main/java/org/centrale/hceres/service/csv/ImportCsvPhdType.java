package org.centrale.hceres.service.csv;


import org.centrale.hceres.dto.csv.CsvPhdType;
import org.centrale.hceres.dto.csv.ImportCsvSummary;
import org.centrale.hceres.dto.csv.utils.GenericCsv;
import org.centrale.hceres.items.PhdType;
import org.centrale.hceres.repository.PhdTypeRepository;
import org.centrale.hceres.service.csv.util.GenericCsvImporter;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImportCsvPhdType {

    @Autowired
    private PhdTypeRepository phdTypeRepository;

    /**
     * @param csvRows          List of csv data
     * @param importCsvSummary Summary of the import
     * @return Map from csv id to {@link CsvPhdType}
     */
    public Map<Integer, GenericCsv<PhdType, Integer>> importCsvList(List<?> csvRows, ImportCsvSummary importCsvSummary) {
        return new GenericCsvImporter<PhdType, Integer>()
                .importCsvList(csvRows,
                        CsvPhdType::new,
                        phdTypeRepository,
                        SupportedCsvTemplate.PHD_TYPE,
                        importCsvSummary);
    }
}
