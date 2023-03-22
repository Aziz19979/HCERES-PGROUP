package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.*;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

// currently invited oral communication does not have associated activity
// it seem that OralComPoster defined in the project correspond
// OralComPoster
@EqualsAndHashCode(callSuper = true)
@Data
public class CsvOralComPoster extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvOralComPoster;
    private static final int ID_CSV_ORAL_COM_POSTER_ORDER = 0;
    private Integer year;
    private static final int YEAR_ORDER = 1;
    private Integer idTypeCom;
    private static final int ID_TYPE_COM_ORDER = 2;
    private Integer idChoiceMeeting;
    private static final int ID_CHOICE_MEETING_ORDER = 3;
    private String title;
    private static final int TITLE_ORDER = 4;
    private String authors;
    private static final int AUTHORS_ORDER = 5;
    private String nameMeeting;
    private static final int NAME_MEETING_ORDER = 6;
    private java.sql.Date date;
    private static final int DATE_ORDER = 7;
    private String location;
    private static final int LOCATION_ORDER = 8;


    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvOralComPoster(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions{
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORAL_COM_POSTER_ORDER,
                        f -> this.setIdCsvOralComPoster(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(YEAR_ORDER,
                        f -> this.setYear(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_COM_ORDER,
                        f -> this.setIdTypeCom(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(ID_CHOICE_MEETING_ORDER,
                        f -> this.setIdChoiceMeeting(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(TITLE_ORDER,
                        f -> this.setTitle(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(AUTHORS_ORDER,
                        f -> this.setAuthors(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(NAME_MEETING_ORDER,
                        f -> this.setNameMeeting(RequestParser.getAsString(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(DATE_ORDER,
                        f -> {
                            // there is a big problem parsing date in this field as it is not in the same format as the other date
                            // it may contain also interval of dates
                            /// this.setDate(RequestParser.getAsDateCsvFormatCsvFormat(csvData.get(f)))
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, this.getYear());
                            this.setDate(new java.sql.Date(calendar.getTime().getTime()));
                        }),
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
        activity.setIdTypeActivity(TypeActivityId.ORAL_COMMUNICATION_POSTER.getId());

        OralComPoster oralComPoster = new OralComPoster();
        // direct use of id present in database via sql type_oral_com_poster
        oralComPoster.setTypeOralComPosterId(this.getIdTypeCom());

        // field could not be interpreted this.getIdChoiceMeeting()

        oralComPoster.setOralComPosterTitle(this.getTitle());
        oralComPoster.setOralComPosterDate(this.getDate());
        oralComPoster.setAuthors(this.getAuthors());

        Meeting meeting = new Meeting();
        meeting.setMeetingName(this.getNameMeeting());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getDate());
        meeting.setMeetingYear(calendar.get(Calendar.YEAR));
        meeting.setMeetingStart(this.getDate());
        meeting.setMeetingLocation(this.getLocation());
        oralComPoster.setMeeting(meeting);

        oralComPoster.setActivity(activity);

        activity.setOralComPoster(oralComPoster);
        return activity;
    }


    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getYear()
                + "_" + this.getIdTypeCom()
                + "_" + this.getTitle()
                + "_" + this.getAuthors()
                + "_" + this.getNameMeeting()
                + "_" + this.getDate()
                + "_" + this.getLocation()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getOralComPoster().getMeeting().getMeetingYear()
                + "_" + entity.getOralComPoster().getTypeOralComPosterId()
                + "_" + entity.getOralComPoster().getOralComPosterTitle()
                + "_" + entity.getOralComPoster().getAuthors()
                + "_" + entity.getOralComPoster().getMeeting().getMeetingName()
                + "_" + entity.getOralComPoster().getOralComPosterDate()
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
