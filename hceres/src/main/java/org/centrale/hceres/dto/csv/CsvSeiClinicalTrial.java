package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.SeiClinicalTrial;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvSeiClinicalTrial extends DependentCsv<Activity, Integer> {
//    id_activity;start_date;coordinator_partner;title_clinical_trial;end_date;registration_nb;sponsor_name;included_patients_nb;funding;funding_amount

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvSeiClinicalTrial;
    private static final int ID_CSV_SEI_CLINICAL_TRIAL_ORDER = 0;
    private java.sql.Date startDate;
    private static final int START_DATE_ORDER = 1;
    private Boolean coordinatorPartner;
    private static final int COORDINATOR_PARTNER_ORDER = 2;
    private String titleClinicalTrial;
    private static final int TITLE_CLINICAL_TRIAL_ORDER = 3;
    private java.sql.Date endDate;
    private static final int END_DATE_ORDER = 4;
    private String registrationNb;
    private static final int REGISTRATION_NB_ORDER = 5;
    private String sponsorName;
    private static final int SPONSOR_NAME_ORDER = 6;
    private Integer includedPatientsNb;
    private static final int INCLUDED_PATIENTS_NB_ORDER = 7;
    private String funding;
    private static final int FUNDING_ORDER = 8;
    private Integer fundingAmount;
    private static final int FUNDING_AMOUNT_ORDER = 9;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvSeiClinicalTrial(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_SEI_CLINICAL_TRIAL_ORDER,
                        f -> this.setIdCsvSeiClinicalTrial(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(START_DATE_ORDER,
                        f -> this.setStartDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(COORDINATOR_PARTNER_ORDER,
                        f -> this.setCoordinatorPartner(RequestParser.getAsBoolean(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(TITLE_CLINICAL_TRIAL_ORDER,
                        f -> this.setTitleClinicalTrial(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(END_DATE_ORDER,
                        f -> this.setEndDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(REGISTRATION_NB_ORDER,
                        f -> this.setRegistrationNb(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(SPONSOR_NAME_ORDER,
                        f -> this.setSponsorName(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(INCLUDED_PATIENTS_NB_ORDER,
                        f -> this.setIncludedPatientsNb(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(FUNDING_ORDER,
                        f -> this.setFunding(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(FUNDING_AMOUNT_ORDER,
                        f -> this.setFundingAmount(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_SEI_CLINICAL_TRIAL_ORDER,
                        this.getIdCsvSeiClinicalTrial(),
                        SupportedCsvTemplate.SEI_CLINICAL_TRIAL,
                        this.activityIdCsvMap.get(this.getIdCsvSeiClinicalTrial()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.SEI_CLINICAL_TRIAL.getId());
        SeiClinicalTrial seiClinicalTrial = new SeiClinicalTrial();
        seiClinicalTrial.setStartDate(this.getStartDate());
        seiClinicalTrial.setCoordinatorPartner(this.getCoordinatorPartner());
        seiClinicalTrial.setTitleClinicalTrial(this.getTitleClinicalTrial());
        seiClinicalTrial.setEndDate(this.getEndDate());
        seiClinicalTrial.setRegistrationNb(this.getRegistrationNb());
        seiClinicalTrial.setSponsorName(this.getSponsorName());
        seiClinicalTrial.setIncludedPatientsNb(this.getIncludedPatientsNb());
        seiClinicalTrial.setFunding(this.getFunding());
        seiClinicalTrial.setFundingAmount(this.getFundingAmount());

        activity.setSeiClinicalTrial(seiClinicalTrial);
        seiClinicalTrial.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getStartDate()
                + "_" + this.getCoordinatorPartner()
                + "_" + this.getTitleClinicalTrial()
                + "_" + this.getEndDate()
                + "_" + this.getRegistrationNb()
                + "_" + this.getSponsorName()
                + "_" + this.getIncludedPatientsNb()
                + "_" + this.getFunding()
                + "_" + this.getFundingAmount()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getSeiClinicalTrial().getStartDate()
                + "_" + entity.getSeiClinicalTrial().getCoordinatorPartner()
                + "_" + entity.getSeiClinicalTrial().getTitleClinicalTrial()
                + "_" + entity.getSeiClinicalTrial().getEndDate()
                + "_" + entity.getSeiClinicalTrial().getRegistrationNb()
                + "_" + entity.getSeiClinicalTrial().getSponsorName()
                + "_" + entity.getSeiClinicalTrial().getIncludedPatientsNb()
                + "_" + entity.getSeiClinicalTrial().getFunding()
                + "_" + entity.getSeiClinicalTrial().getFundingAmount()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvSeiClinicalTrial();
    }
}
