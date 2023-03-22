package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvPublication;
import org.centrale.hceres.dto.csv.ImportCsvSummary;
import org.centrale.hceres.dto.csv.utils.GenericCsv;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.PublicationType;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.repository.ActivityRepository;
import org.centrale.hceres.service.csv.util.GenericCsvImporter;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
public class ImportCsvPublication {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param publicationRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> publicationRows, ImportCsvSummary importCsvSummary,
                                                                     Map<Integer, CsvActivity> activityMap,
                                                                     Map<Integer, GenericCsv<PublicationType, Integer>> publicationTypeMap) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                publicationRows,
                () -> new CsvPublication(activityMap, publicationTypeMap),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.PUBLICATION.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.PUBLICATION,
                importCsvSummary);
    }
}
