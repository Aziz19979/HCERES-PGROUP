package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.InternationalCollaboration;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInternationalCollaboration extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvInternationalCollaboration;
    private static final int ID_CSV_INTERNATIONAL_COLLABORATION_ORDER = 0;

    private java.sql.Date dateProjectStart;
    private static final int DATE_PROJECT_START_ORDER = 1;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 2;
    private String partnerEntity;
    private static final int PARTNER_ENTITY_ORDER = 3;
    private String countryStateCity;
    private static final int COUNTRY_STATE_CITY_ORDER = 4;
    private String piPartners;
    private static final int PI_PARTNERS_ORDER = 5;
    private String mailPartners;
    private static final int MAIL_PARTNERS_ORDER = 6;
    private Boolean activeProject;
    private static final int ACTIVE_PROJECT_ORDER = 7;
    private String refJointPublication;
    private static final int REF_JOINT_PUBLICATION_ORDER = 8;
    private Boolean umr1064Coordinated;
    private static final int UMR1064_COORDINATED_ORDER = 9;
    private Boolean agreementSigned;
    private static final int AGREEMENT_SIGNED_ORDER = 10;
    private Integer numberResultingPublications;
    private static final int NUMBER_RESULTING_PUBLICATIONS_ORDER = 11;
    private String associatedFunding;
    private static final int ASSOCIATED_FUNDING_ORDER = 12;


    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvInternationalCollaboration(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_INTERNATIONAL_COLLABORATION_ORDER,
                        f -> this.setIdCsvInternationalCollaboration(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(DATE_PROJECT_START_ORDER,
                        f -> this.setDateProjectStart(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(PARTNER_ENTITY_ORDER,
                        f -> this.setPartnerEntity(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(COUNTRY_STATE_CITY_ORDER,
                        f -> this.setCountryStateCity(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(PI_PARTNERS_ORDER,
                        f -> this.setPiPartners(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(MAIL_PARTNERS_ORDER,
                        f -> this.setMailPartners(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ACTIVE_PROJECT_ORDER,
                        f -> this.setActiveProject(RequestParser.getAsBoolean(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(REF_JOINT_PUBLICATION_ORDER,
                        f -> this.setRefJointPublication(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(UMR1064_COORDINATED_ORDER,
                        f -> this.setUmr1064Coordinated(RequestParser.getAsBoolean(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(AGREEMENT_SIGNED_ORDER,
                        f -> this.setAgreementSigned(RequestParser.getAsBoolean(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(NUMBER_RESULTING_PUBLICATIONS_ORDER,
                        f -> this.setNumberResultingPublications(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ASSOCIATED_FUNDING_ORDER,
                        f -> this.setAssociatedFunding(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_INTERNATIONAL_COLLABORATION_ORDER,
                        this.getIdCsvInternationalCollaboration(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvInternationalCollaboration()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.NATIONAL_INTERNATIONAL_COLLABORATION.getId());
        InternationalCollaboration internationalCollaboration = new InternationalCollaboration();
        internationalCollaboration.setDateProjectStart(this.getDateProjectStart());
        internationalCollaboration.setTypeCollabId(this.getIdType());
        internationalCollaboration.setPartnerEntity(this.getPartnerEntity());
        internationalCollaboration.setCountryStateCity(this.getCountryStateCity());
        internationalCollaboration.setPiPartners(this.getPiPartners());
        internationalCollaboration.setMailPartners(this.getMailPartners());
        internationalCollaboration.setActiveProject(this.getActiveProject());
        internationalCollaboration.setRefJointPublication(this.getRefJointPublication());
        internationalCollaboration.setUmrCoordinated(this.getUmr1064Coordinated());
        internationalCollaboration.setAgreementSigned(this.getAgreementSigned());
        internationalCollaboration.setNumberResultingPublications(this.getNumberResultingPublications());
        internationalCollaboration.setAssociatedFunding(this.getAssociatedFunding());

        activity.setInternationalCollaboration(internationalCollaboration);
        internationalCollaboration.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDateProjectStart()
                + "_" + this.getIdType()
                + "_" + this.getPartnerEntity()
                + "_" + this.getCountryStateCity()
                + "_" + this.getPiPartners()
                + "_" + this.getMailPartners()
                + "_" + this.getActiveProject()
                + "_" + this.getRefJointPublication()
                + "_" + this.getUmr1064Coordinated()
                + "_" + this.getAgreementSigned()
                + "_" + this.getNumberResultingPublications()
                + "_" + this.getAssociatedFunding()).toLowerCase();

    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getInternationalCollaboration().getDateProjectStart()
                + "_" + entity.getInternationalCollaboration().getTypeCollabId()
                + "_" + entity.getInternationalCollaboration().getPartnerEntity()
                + "_" + entity.getInternationalCollaboration().getCountryStateCity()
                + "_" + entity.getInternationalCollaboration().getPiPartners()
                + "_" + entity.getInternationalCollaboration().getMailPartners()
                + "_" + entity.getInternationalCollaboration().getActiveProject()
                + "_" + entity.getInternationalCollaboration().getRefJointPublication()
                + "_" + entity.getInternationalCollaboration().getUmrCoordinated()
                + "_" + entity.getInternationalCollaboration().getAgreementSigned()
                + "_" + entity.getInternationalCollaboration().getNumberResultingPublications()
                + "_" + entity.getInternationalCollaboration().getAssociatedFunding()).toLowerCase();

    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvInternationalCollaboration();
    }
}
