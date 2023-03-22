package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.*;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

// currently invited oral communication does not have associated activity
// it seem that OralComPoster defined in the project correspond
// OralComPoster
@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInvitedOralCommunication extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvOralComPoster;
    private static final int ID_CSV_ORAL_COM_POSTER_ORDER = 0;
    private java.sql.Date dateCommunication;
    private static final int DATE_COMMUNICATION_ORDER = 1;
    private String title;
    private static final int TITLE_ORDER = 2;
    private String nameMeeting;
    private static final int NAME_MEETING_ORDER = 3;
    private java.sql.Date dateMeeting;
    private static final int DATE_MEETING_ORDER = 4;
    private String location;
    private static final int LOCATION_ORDER = 5;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvInvitedOralCommunication(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }


    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORAL_COM_POSTER_ORDER,
                        f -> this.setIdCsvOralComPoster(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(DATE_COMMUNICATION_ORDER,
                        f -> this.setDateCommunication(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(TITLE_ORDER,
                        f -> this.setTitle(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(NAME_MEETING_ORDER,
                        f -> this.setNameMeeting(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(DATE_MEETING_ORDER,
                        f -> this.setDateMeeting(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(LOCATION_ORDER,
                        f -> this.setLocation(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_ORAL_COM_POSTER_ORDER,
                        this.getIdCsvOralComPoster(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvOralComPoster()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.csvActivity.convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.INVITED_ORAL_COMMUNICATION.getId());

        OralComPoster oralComPoster = new OralComPoster();
        oralComPoster.setOralComPosterDate(this.getDateCommunication());
        oralComPoster.setOralComPosterTitle(this.getTitle());
        // using default value for authors as imported by csv
        oralComPoster.setAuthors("Importé par csv");

        Meeting meeting = new Meeting();
        meeting.setMeetingName(this.getNameMeeting());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getDateMeeting());
        meeting.setMeetingYear(calendar.get(Calendar.YEAR));
        meeting.setMeetingStart(this.getDateMeeting());
        meeting.setMeetingLocation(this.getLocation());
        oralComPoster.setMeeting(meeting);

        // currently taking default type
        oralComPoster.setTypeOralComPosterId(1);

        oralComPoster.setActivity(activity);

        activity.setOralComPoster(oralComPoster);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDateCommunication()
                + "_" + this.getTitle()
                + "_" + this.getNameMeeting()
                + "_" + this.getDateMeeting()
                + "_" + this.getLocation()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getOralComPoster().getOralComPosterDate()
                + "_" + entity.getOralComPoster().getOralComPosterTitle()
                + "_" + entity.getOralComPoster().getMeeting().getMeetingName()
                + "_" + entity.getOralComPoster().getMeeting().getMeetingStart()
                + "_" + entity.getOralComPoster().getMeeting().getMeetingLocation()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvOralComPoster();
    }
}
