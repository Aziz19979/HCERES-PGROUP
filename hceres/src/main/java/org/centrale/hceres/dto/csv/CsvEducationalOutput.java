package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.EducationalOutput;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvEducationalOutput extends DependentCsv<Activity, Integer> {
//    id_activity;completion_date;id_type;description

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvEducationalOutput;
    private static final int ID_CSV_EDUCATIONAL_OUTPUT_ORDER = 0;

    private java.sql.Date completionDate;
    private static final int COMPLETION_DATE_ORDER = 1;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 2;
    private String description;
    private static final int DESCRIPTION_ORDER = 3;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvEducationalOutput(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_EDUCATIONAL_OUTPUT_ORDER,
                        f -> this.setIdCsvEducationalOutput(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(COMPLETION_DATE_ORDER,
                        f -> this.setCompletionDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(DESCRIPTION_ORDER,
                        f -> this.setDescription(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_EDUCATIONAL_OUTPUT_ORDER,
                        this.getIdCsvEducationalOutput(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvEducationalOutput()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.EDUCATIONAL_OUTPUT.getId());
        EducationalOutput educationalOutput = new EducationalOutput();
        educationalOutput.setCompletionDate(this.getCompletionDate());
        educationalOutput.setIdType(this.getIdType());
        educationalOutput.setDescription(this.getDescription());
        activity.setEducationalOutput(educationalOutput);
        educationalOutput.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getCompletionDate()
                + "_" + this.getIdType()
                + "_" + this.getDescription()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getEducationalOutput().getCompletionDate()
                + "_" + entity.getEducationalOutput().getIdType()
                + "_" + entity.getEducationalOutput().getDescription()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvEducationalOutput();
    }
}
