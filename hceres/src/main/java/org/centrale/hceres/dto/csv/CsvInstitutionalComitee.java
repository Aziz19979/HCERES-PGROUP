package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.InstitutionalComitee;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInstitutionalComitee extends DependentCsv<Activity, Integer> {

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvInstitutionalComitee;
    private static final int ID_CSV_INSTITUTIONAL_COMITEE_ORDER = 0;

    private Integer year;
    private static final int YEAR_ORDER = 1;
    private String nameInstitutionalComitee;
    private static final int NAME_INSTITUTIONAL_COMITEE_ORDER = 2;
    private Integer idRolePiLabEval;
    private static final int ID_ROLE_PI_LAB_EVAL_ORDER = 3;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvInstitutionalComitee(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_INSTITUTIONAL_COMITEE_ORDER,
                        f -> this.setIdCsvInstitutionalComitee(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(YEAR_ORDER,
                        f -> this.setYear(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(NAME_INSTITUTIONAL_COMITEE_ORDER,
                        f -> this.setNameInstitutionalComitee(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(ID_ROLE_PI_LAB_EVAL_ORDER,
                        f -> this.setIdRolePiLabEval(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_INSTITUTIONAL_COMITEE_ORDER,
                        this.getIdCsvInstitutionalComitee(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvInstitutionalComitee()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.RESPONSIBILITY_INSTITUTIONAL_COMITEE_JURY.getId());
        InstitutionalComitee institutionalComitee = new InstitutionalComitee();
        institutionalComitee.setYear(this.getYear());
        institutionalComitee.setInstitutionalComiteeName(this.getNameInstitutionalComitee());
        // clean and verfiy after doctor gives sql insert values for roles
        institutionalComitee.setLaboratoryEvaluationRoleId(this.getIdRolePiLabEval());

        activity.setInstitutionalComitee(institutionalComitee);
        institutionalComitee.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getYear()
                + "_" + this.getNameInstitutionalComitee()
                + "_" + this.getIdRolePiLabEval()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getInstitutionalComitee().getYear()
                + "_" + entity.getInstitutionalComitee().getInstitutionalComiteeName()
                + "_" + entity.getInstitutionalComitee().getLaboratoryEvaluationRoleId()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvInstitutionalComitee();
    }
}
