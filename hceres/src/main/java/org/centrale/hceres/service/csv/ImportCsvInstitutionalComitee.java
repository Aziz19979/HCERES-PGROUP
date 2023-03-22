package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvInstitutionalComitee;
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
public class ImportCsvInstitutionalComitee {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param institutionalComiteeRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> institutionalComiteeRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                institutionalComiteeRows,
                () -> new CsvInstitutionalComitee(activityMap),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.RESPONSIBILITY_INSTITUTIONAL_COMITEE_JURY.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.RESPONSIBILITY_INSTITUTIONAL_COMITEE_JURY,
                importCsvSummary);
    }
}
