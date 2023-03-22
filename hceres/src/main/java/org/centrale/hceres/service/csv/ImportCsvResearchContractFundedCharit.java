package org.centrale.hceres.service.csv;

import lombok.Data;
import org.centrale.hceres.dto.csv.CsvActivity;
import org.centrale.hceres.dto.csv.CsvResearchContractFundedCharit;
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
public class ImportCsvResearchContractFundedCharit {

    @Autowired
    private ActivityRepository activityRepo;

    /**
     * @param researchContractFundedCharitRows      list of array having fields as defined in csv
     * @param importCsvSummary Summary of the import
     */
    public Map<Integer, GenericCsv<Activity, Integer>> importCsvList(List<?> researchContractFundedCharitRows, ImportCsvSummary importCsvSummary,
                                                                    Map<Integer, CsvActivity> activityMap) {
        return new GenericCsvImporter<Activity, Integer>().importCsvList(
                researchContractFundedCharitRows,
                () -> new CsvResearchContractFundedCharit(activityMap),
                () -> activityRepo.findByIdTypeActivity(TypeActivityId.RESEARCH_CONTRACT_FUNDED_PUBLIC_CHARITABLE_INST.getId()),
                activityRepo::saveAll,
                SupportedCsvTemplate.RESEARCH_CONTRACT_FUNDED_PUBLIC_CHARITABLE_INST,
                importCsvSummary);
    }
}
