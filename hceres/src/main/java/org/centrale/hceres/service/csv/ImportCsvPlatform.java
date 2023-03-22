package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvPlatform;
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
public class ImportCsvPlatform {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param platformRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> platformRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                platformRows,
                () -> new CsvPlatform(activityMap),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.PLATFORM.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.PLATFORM,
                importCsvSummary);
    }
}
