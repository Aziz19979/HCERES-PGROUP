package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.ResearchContractFundedCharit;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvResearchContractFundedCharit extends DependentCsv<Activity, Integer> {

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvResearchContractFundedCharit;
    private static final int ID_CSV_RESEARCH_CONTRACT_FUNDED_CHARIT_ORDER = 0;

    private java.sql.Date dateContractAward;
    private static final int DATE_CONTRACT_AWARD_ORDER = 1;
    private String fundingInstitution;
    private static final int FUNDING_INSTITUTION_ORDER = 2;
    private String projectTitle;
    private static final int PROJECT_TITLE_ORDER = 3;
    private Integer startYear;
    private static final int START_YEAR_ORDER = 4;
    private Integer endYear;
    private static final int END_YEAR_ORDER = 5;
    private Integer grantAmount;
    private static final int GRANT_AMOUNT_ORDER = 6;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 7;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvResearchContractFundedCharit(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }
    //        @Override
//    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
//        CsvParserUtil.wrapCsvAllFieldExceptions(
//                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_PUBLICATION_ORDER,
//                        f -> this.setIdCsvPublication(RequestParser.getAsInteger(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(TITLE_ORDER,
//                        f -> this.setTitle(RequestParser.getAsString(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(AUTHORS_ORDER,
//                        f -> this.setAuthors(RequestParser.getAsString(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(SOURCE_ORDER,
//                        f -> this.setSource(RequestParser.getAsString(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(PUBLICATION_DATE_ORDER,
//                        f -> this.setPublicationDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(PMID_ORDER,
//                        f -> this.setPmid(RequestParser.getAsString(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(IMPACT_FACTOR_ORDER,
//                        f -> this.setImpactFactor(RequestParser.getAsBigDecimal(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(CLINIC_ORDER,
//                        f -> this.setClinic(RequestParser.getAsBoolean(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(PDC_ORDER,
//                        f -> this.setPdc(RequestParser.getAsBoolean(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(COLAB_INTER_ORDER,
//                        f -> this.setColabInter(RequestParser.getAsBoolean(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(COLAB_INTRA_CRTI_ORDER,
//                        f -> this.setColabIntraCrti(RequestParser.getAsBoolean(csvData.get(f)))),
//
//                () -> CsvParserUtil.wrapCsvParseException(ID_CHOICE_ORDER,
//                        f -> this.setIdChoice(RequestParser.getAsInteger(csvData.get(f))))
//        );
//    }
//
//    @Override
//    public void initializeDependencies() throws CsvAllFieldExceptions {
//        CsvParserUtil.wrapCsvAllFieldExceptions(
//                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_PUBLICATION_ORDER,
//                        this.getIdCsvPublication(),
//                        SupportedCsvTemplate.PUBLICATION,
//                        this.activityIdCsvMap.get(this.getIdCsvPublication()),
//                        this::setCsvActivity),
//
//                () -> CsvParserUtil.wrapCsvDependencyException(ID_CHOICE_ORDER,
//                        this.getIdChoice(),
//                        SupportedCsvTemplate.PUBLICATION_TYPE,
//                        this.publicationTypeIdCsvMap.get(this.getIdChoice()),
//                        this::setCsvPublicationType)
//        );
//    }
    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_RESEARCH_CONTRACT_FUNDED_CHARIT_ORDER,
                        f -> this.setIdCsvResearchContractFundedCharit(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(DATE_CONTRACT_AWARD_ORDER,
                        f -> this.setDateContractAward(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(FUNDING_INSTITUTION_ORDER,
                        f -> this.setFundingInstitution(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(PROJECT_TITLE_ORDER,
                        f -> this.setProjectTitle(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(START_YEAR_ORDER,
                        f -> this.setStartYear(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(END_YEAR_ORDER,
                        f -> this.setEndYear(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(GRANT_AMOUNT_ORDER,
                        f -> this.setGrantAmount(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_RESEARCH_CONTRACT_FUNDED_CHARIT_ORDER,
                        this.getIdCsvResearchContractFundedCharit(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvResearchContractFundedCharit()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.RESEARCH_CONTRACT_FUNDED_PUBLIC_CHARITABLE_INST.getId());
        ResearchContractFundedCharit researchContractFundedCharit = new ResearchContractFundedCharit();
        researchContractFundedCharit.setDateContractAward(this.getDateContractAward());
        researchContractFundedCharit.setFundingInstitution(this.getFundingInstitution());
        researchContractFundedCharit.setProjectTitle(this.getProjectTitle());
        researchContractFundedCharit.setStartYear(this.getStartYear());
        researchContractFundedCharit.setEndYear(this.getEndYear());
        researchContractFundedCharit.setGrantAmount(this.getGrantAmount());
        researchContractFundedCharit.setTypeResearchContractId(this.getIdType());
        activity.setResearchContractFundedCharit(researchContractFundedCharit);
        researchContractFundedCharit.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDateContractAward()
                + "_" + this.getFundingInstitution()
                + "_" + this.getProjectTitle()
                + "_" + this.getStartYear()
                + "_" + this.getEndYear()
                + "_" + this.getGrantAmount()
                + "_" + this.getIdType()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getResearchContractFundedCharit().getDateContractAward()
                + "_" + entity.getResearchContractFundedCharit().getFundingInstitution()
                + "_" + entity.getResearchContractFundedCharit().getProjectTitle()
                + "_" + entity.getResearchContractFundedCharit().getStartYear()
                + "_" + entity.getResearchContractFundedCharit().getEndYear()
                + "_" + entity.getResearchContractFundedCharit().getGrantAmount()
                + "_" + entity.getResearchContractFundedCharit().getTypeResearchContractId()).toLowerCase();

    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvResearchContractFundedCharit();
    }
}
