package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.InvolvementTrainingPedagogical;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInvolvementTrainingPedagogical extends DependentCsv<Activity, Integer> {

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvInvolvementTrainingPedagogical;
    private static final int ID_CSV_INVOLVEMENT_TRAINING_PEDAGOGICAL_ORDER = 0;

    private Integer year;
    private static final int YEAR_ORDER = 1;
    private String nameMaster;
    private static final int NAME_MASTER_ORDER = 2;

    // check later what default value to put here
    // or does read it from the csv (require to change the csv) ?
    private static final int DEFAULT_ID_TYPE = 1;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 3;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvInvolvementTrainingPedagogical(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_INVOLVEMENT_TRAINING_PEDAGOGICAL_ORDER,
                        f -> this.setIdCsvInvolvementTrainingPedagogical(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(YEAR_ORDER,
                        f -> this.setYear(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(NAME_MASTER_ORDER,
                        f -> this.setNameMaster(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_INVOLVEMENT_TRAINING_PEDAGOGICAL_ORDER,
                        this.getIdCsvInvolvementTrainingPedagogical(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvInvolvementTrainingPedagogical()),
                        this::setCsvActivity)
        );
    }
    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.INVOLVEMENT_TRAINING_PEDAGOGICAL_RESPONSIBILITY.getId());
        InvolvementTrainingPedagogical involvementTrainingPedagogical = new InvolvementTrainingPedagogical();
        involvementTrainingPedagogical.setYear(this.getYear());
        involvementTrainingPedagogical.setNameMaster(this.getNameMaster());
        involvementTrainingPedagogical.setIdType(this.getIdType());
        activity.setInvolvementTrainingPedagogical(involvementTrainingPedagogical);
        involvementTrainingPedagogical.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getYear()
                + "_" + this.getNameMaster()
                + "_" + this.getIdType()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getInvolvementTrainingPedagogical().getYear()
                + "_" + entity.getInvolvementTrainingPedagogical().getNameMaster()
                + "_" + entity.getInvolvementTrainingPedagogical().getIdType()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvInvolvementTrainingPedagogical();
    }
}
