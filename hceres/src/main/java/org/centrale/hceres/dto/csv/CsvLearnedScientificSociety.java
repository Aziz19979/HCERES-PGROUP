package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.*;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvLearnedScientificSociety extends DependentCsv<Activity, Integer> {

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvLearnedScientificSociety;
    private static final int ID_CSV_LEARNED_SCIENTIFIC_SOCIETY_ORDER = 0;

    private java.sql.Date startDate;
    private static final int START_DATE_ORDER = 1;
    private java.sql.Date endDate;
    private static final int END_DATE_ORDER = 2;
    private String scientificSocietyName;
    private static final int SCIENTIFIC_SOCIETY_NAME_ORDER = 3;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvLearnedScientificSociety(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_LEARNED_SCIENTIFIC_SOCIETY_ORDER,
                        f -> this.setIdCsvLearnedScientificSociety(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(START_DATE_ORDER,
                        f -> this.setStartDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(END_DATE_ORDER,
                        f -> this.setEndDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(SCIENTIFIC_SOCIETY_NAME_ORDER,
                        f -> this.setScientificSocietyName(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_LEARNED_SCIENTIFIC_SOCIETY_ORDER,
                        this.getIdCsvLearnedScientificSociety(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvLearnedScientificSociety()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.SR_RESPONSIBILITY_LEARNED_SCIENTIFIC_SOCIETY.getId());
        LearnedScientificSociety learnedScientificSociety = new LearnedScientificSociety();
        learnedScientificSociety.setStartDate(this.getStartDate());
        learnedScientificSociety.setEndDate(this.getEndDate());
        learnedScientificSociety.setLearnedScientificSocietyName(this.getScientificSocietyName());

        // currently setting role id to 1
        // check later if there will be additional csv input
        learnedScientificSociety.setLearnedScientificSocietyRoleId(1);

        activity.setLearnedScientificSociety(learnedScientificSociety);
        learnedScientificSociety.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getStartDate()
                + "_" + this.getEndDate()
                + "_" + this.getScientificSocietyName()).toLowerCase();

    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getLearnedScientificSociety().getStartDate()
                + "_" + entity.getLearnedScientificSociety().getEndDate()
                + "_" + entity.getLearnedScientificSociety().getLearnedScientificSocietyName()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvLearnedScientificSociety();
    }
}
