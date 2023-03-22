package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.InvitedSeminar;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInvitedSeminar extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvInvitedSeminar;
    private static final int ID_CSV_INVITED_SEMINAR_ORDER = 0;
    private java.sql.Date date;
    private static final int DATE_ORDER = 1;
    private String titleSeminar;
    private static final int TITLE_SEMINAR_ORDER = 2;
    private String location;
    private static final int LOCATION_ORDER = 3;
    private String invitedBy;
    private static final int INVITED_BY_ORDER = 4;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvInvitedSeminar(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }


    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_INVITED_SEMINAR_ORDER,
                        f -> this.setIdCsvInvitedSeminar(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(DATE_ORDER,
                        f -> this.setDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(TITLE_SEMINAR_ORDER,
                        f -> this.setTitleSeminar(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(LOCATION_ORDER,
                        f -> this.setLocation(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(INVITED_BY_ORDER,
                        f -> this.setInvitedBy(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_INVITED_SEMINAR_ORDER,
                        this.getIdCsvInvitedSeminar(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvInvitedSeminar()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.INVITED_SEMINAR.getId());
        InvitedSeminar invitedSeminar = new InvitedSeminar();
        invitedSeminar.setDate(this.getDate());
        invitedSeminar.setTitleSeminar(this.getTitleSeminar());
        invitedSeminar.setLocation(this.getLocation());
        invitedSeminar.setInvitedBy(this.getInvitedBy());
        invitedSeminar.setActivity(activity);
        activity.setInvitedSeminar(invitedSeminar);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDate()
                + "_" + this.getTitleSeminar()
                + "_" + this.getLocation()
                + "_" + this.getInvitedBy()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getInvitedSeminar().getDate()
                + "_" + entity.getInvitedSeminar().getTitleSeminar()
                + "_" + entity.getInvitedSeminar().getLocation()
                + "_" + entity.getInvitedSeminar().getInvitedBy()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvInvitedSeminar();
    }
}
