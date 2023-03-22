package org.centrale.hceres.service.csv;


import org.centrale.hceres.dto.csv.CsvPublicationType;
import org.centrale.hceres.dto.csv.ImportCsvSummary;
import org.centrale.hceres.dto.csv.utils.GenericCsv;
import org.centrale.hceres.items.PublicationType;
import org.centrale.hceres.repository.PublicationTypeRepository;
import org.centrale.hceres.service.csv.util.GenericCsvImporter;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImportCsvPublicationType {

    @Autowired
    private PublicationTypeRepository publicationTypeRepository;

    /**
     * @param csvRows          List of csv data
     * @param importCsvSummary Summary of the import
     * @return Map from csv id to {@link CsvPublicationType}
     */
    public Map<Integer, GenericCsv<PublicationType, Integer>> importCsvList(List<?> csvRows, ImportCsvSummary importCsvSummary) {
        return new GenericCsvImporter<PublicationType, Integer>()
                .importCsvList(csvRows,
                        CsvPublicationType::new,
                        publicationTypeRepository,
                        SupportedCsvTemplate.PUBLICATION_TYPE,
                        importCsvSummary);
    }
}
