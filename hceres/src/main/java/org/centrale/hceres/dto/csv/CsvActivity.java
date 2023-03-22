package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.Researcher;
import org.centrale.hceres.items.TypeActivity;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class is used only for mapping between activityTemplate.csv and Researcher.csv
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CsvActivity extends DependentCsv<Activity, Integer> {
    // id_type;id_activity;id_researcher;specific_activity_count;activity_name_type
    private Integer idCsvTypeActivity;
    private static final int ID_CSV_TYPE_ACTIVITY_ORDER = 0;
    private GenericCsv<TypeActivity, Integer> csvTypeActivity;
    private final Map<Integer, GenericCsv<TypeActivity, Integer>> typeActivityIdCsvMap;

    private Integer idCsv;
    private static final int ID_CSV_ORDER = 1;

    private Integer idCsvResearcher;
    private static final int ID_CSV_RESEARCHER_ORDER = 2;
    private GenericCsv<Researcher, Integer> csvResearcher;
    private final Map<Integer, GenericCsv<Researcher, Integer>> researcherIdCsvMap;

    // specific count along with idCsvTypeActivity give the id
    // of activity
    private Integer specificActivityCount;
    private static final int SPECIFIC_ACTIVITY_COUNT_ORDER = 3;

    // ignored
    private String activityNameType;
    private static final int ACTIVITY_NAME_TYPE_ORDER = 4;

    public CsvActivity(Map<Integer, GenericCsv<TypeActivity, Integer>> typeActivityIdCsvMap,
                       Map<Integer, GenericCsv<Researcher, Integer>> researcherIdCsvMap) {
        this.typeActivityIdCsvMap = typeActivityIdCsvMap;
        this.researcherIdCsvMap = researcherIdCsvMap;
    }


    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_TYPE_ACTIVITY_ORDER,
                        f -> this.setIdCsvTypeActivity(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORDER,
                        f -> this.setIdCsv(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_RESEARCHER_ORDER,
                        f -> this.setIdCsvResearcher(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(SPECIFIC_ACTIVITY_COUNT_ORDER,
                        f -> this.setSpecificActivityCount(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ACTIVITY_NAME_TYPE_ORDER,
                        f -> this.setActivityNameType(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_TYPE_ACTIVITY_ORDER,
                        this.getIdCsvTypeActivity(),
                        SupportedCsvTemplate.TYPE_ACTIVITY,
                        this.typeActivityIdCsvMap.get(this.getIdCsvTypeActivity()),
                        this::setCsvTypeActivity),

                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_RESEARCHER_ORDER,
                        this.getIdCsvResearcher(),
                        SupportedCsvTemplate.RESEARCHER,
                        this.researcherIdCsvMap.get(this.getIdCsvResearcher()),
                        this::setCsvResearcher)
        );
    }

    private static final String IMPLEMENTATION_ERROR = "Should not be called, convert Specific Activity instead";

    @Override
    public Activity convertToEntity() {
        Activity activity = new Activity();
        activity.setResearcherList(Collections.singletonList(new Researcher(this.csvResearcher.getIdDatabase())));
        return activity;
    }

    @Override
    public String getMergingKey() {
        throw new UnsupportedOperationException(IMPLEMENTATION_ERROR);
    }

    @Override
    public String getMergingKey(Activity entity) {
        throw new UnsupportedOperationException(IMPLEMENTATION_ERROR);
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.idCsv;
    }
}
