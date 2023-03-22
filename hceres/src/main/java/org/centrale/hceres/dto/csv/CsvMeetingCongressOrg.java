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
public class CsvMeetingCongressOrg extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvMeetingCongressOrg;
    private static final int ID_CSV_MEETING_CONGRESS_ORG_ORDER = 0;

    private Integer year;
    private static final int YEAR_ORDER = 1;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 2;
    private String nameCongress;
    private static final int NAME_CONGRESS_ORDER = 3;
    private java.sql.Date date;
    private static final int DATE_ORDER = 4;
    private String location;
    private static final int LOCATION_ORDER = 5;



    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvMeetingCongressOrg(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_MEETING_CONGRESS_ORG_ORDER,
                        f -> this.setIdCsvMeetingCongressOrg(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(YEAR_ORDER,
                        f -> this.setYear(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsIntegerOrDefault(csvData.get(f), 0))),
                () -> CsvParserUtil.wrapCsvParseException(NAME_CONGRESS_ORDER,
                        f -> this.setNameCongress(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(DATE_ORDER,
                        f -> this.setDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(LOCATION_ORDER,
                        f -> this.setLocation(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_MEETING_CONGRESS_ORG_ORDER,
                        this.getIdCsvMeetingCongressOrg(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvMeetingCongressOrg()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.csvActivity.convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.MEETING_CONGRESS_ORG.getId());

        MeetingCongressOrg meetingCongressOrg = new MeetingCongressOrg();

        Meeting meeting = new Meeting();
        meeting.setMeetingName(this.getNameCongress());
        if (this.getDate() != null) {
            meeting.setMeetingStart(this.getDate());
        }
        meeting.setMeetingYear(this.getYear());
        meeting.setMeetingLocation(this.getLocation());
        meetingCongressOrg.setMeeting(meeting);

        meetingCongressOrg.setActivity(activity);

        activity.setMeetingCongressOrg(meetingCongressOrg);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDate()
                + "_" + this.getNameCongress()
                + "_" + this.getYear()
                + "_" + this.getLocation()).toLowerCase();
    }


    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getMeetingCongressOrg().getMeeting().getMeetingStart()
                + "_" + entity.getMeetingCongressOrg().getMeeting().getMeetingName()
                + "_" + entity.getMeetingCongressOrg().getMeeting().getMeetingYear()
                + "_" + entity.getMeetingCongressOrg().getMeeting().getMeetingLocation()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvMeetingCongressOrg();
    }
}
