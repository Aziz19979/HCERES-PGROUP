package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvEditorialActivity;
import org.centrale.hceres.dto.csv.ImportCsvSummary;
import org.centrale.hceres.dto.csv.utils.GenericCsv;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.repository.ActivityRepository;
import org.centrale.hceres.service.csv.util.GenericCsvImporter;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
public class ImportCsvEditorialActivity {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param editorialActivityRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> editorialActivityRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap,
                                                                     JournalCreatorCache journalCreatorCache) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                editorialActivityRows,
                () -> new CsvEditorialActivity(activityMap, journalCreatorCache),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.EDITORIAL_ACTIVITY.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.EDITORIAL_ACTIVITY,
                importCsvSummary);
    }
}
