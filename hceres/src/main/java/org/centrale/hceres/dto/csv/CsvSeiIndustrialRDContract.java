package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.SeiIndustrialRDContract;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvSeiIndustrialRDContract extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvSeiIndustrialRDContract;
    private static final int ID_CSV_SEI_INDUSTRIAL_RD_CONTRACT_ORDER = 0;
    private java.sql.Date startDate;
    private static final int START_DATE_ORDER = 1;
    private String nameCompanyInvolved;
    private static final int NAME_COMPANY_INVOLVED_ORDER = 2;
    private String projectTitle;
    private static final int PROJECT_TITLE_ORDER = 3;
    private Integer agreementAmount;
    private static final int AGREEMENT_AMOUNT_ORDER = 4;
    private java.sql.Date endDate;
    private static final int END_DATE_ORDER = 5;


    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvSeiIndustrialRDContract(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_SEI_INDUSTRIAL_RD_CONTRACT_ORDER,
                        f -> this.setIdCsvSeiIndustrialRDContract(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(START_DATE_ORDER,
                        f -> this.setStartDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(NAME_COMPANY_INVOLVED_ORDER,
                        f -> this.setNameCompanyInvolved(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(PROJECT_TITLE_ORDER,
                        f -> this.setProjectTitle(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(AGREEMENT_AMOUNT_ORDER,
                        f -> this.setAgreementAmount(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(END_DATE_ORDER,
                        f -> this.setEndDate(RequestParser.getAsDateCsvFormat(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_SEI_INDUSTRIAL_RD_CONTRACT_ORDER,
                        this.getIdCsvSeiIndustrialRDContract(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvSeiIndustrialRDContract()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.SEI_INDUSTRIAL_R_D_CONTRACT.getId());
        SeiIndustrialRDContract seiIndustrialRDContract = new SeiIndustrialRDContract();
        seiIndustrialRDContract.setStartDate(this.getStartDate());
        seiIndustrialRDContract.setNameCompanyInvolved(this.getNameCompanyInvolved());
        seiIndustrialRDContract.setProjectTitle(this.getProjectTitle());
        seiIndustrialRDContract.setAgreementAmount(this.getAgreementAmount());
        seiIndustrialRDContract.setEndDate(this.getEndDate());

        activity.setSeiIndustrialRDContract(seiIndustrialRDContract);
        seiIndustrialRDContract.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getStartDate()
                + "_" + this.getNameCompanyInvolved()
                + "_" + this.getProjectTitle()
                + "_" + this.getAgreementAmount()
                + "_" + this.getEndDate()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getSeiIndustrialRDContract().getStartDate()
                + "_" + entity.getSeiIndustrialRDContract().getNameCompanyInvolved()
                + "_" + entity.getSeiIndustrialRDContract().getProjectTitle()
                + "_" + entity.getSeiIndustrialRDContract().getAgreementAmount()
                + "_" + entity.getSeiIndustrialRDContract().getEndDate()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvSeiIndustrialRDContract();
    }
}
