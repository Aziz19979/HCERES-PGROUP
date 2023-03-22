package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvLearnedScientificSociety;
import org.centrale.hceres.dto.csv.ImportCsvSummary;
import org.centrale.hceres.dto.csv.utils.GenericCsv;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.repository.ActivityRepository;
import org.centrale.hceres.service.csv.util.GenericCsvImporter;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
public class ImportCsvLearnedScientificSociety {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param learnedScientificSocietyRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> learnedScientificSocietyRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                learnedScientificSocietyRows,
                () -> new CsvLearnedScientificSociety(activityMap),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.SR_RESPONSIBILITY_LEARNED_SCIENTIFIC_SOCIETY.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.SR_RESPONSIBILITY_LEARNED_SCIENTIFIC_SOCIETY,
                importCsvSummary);
    }
}
