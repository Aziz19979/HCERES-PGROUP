package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvBook;
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
public class ImportCsvBook {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param bookRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> bookRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap,
                                                                     LanguageCreatorCache languageCreatorCache) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                bookRows,
                () -> new CsvBook(activityMap, languageCreatorCache),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.BOOK.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.BOOK,
                importCsvSummary);
    }
}
