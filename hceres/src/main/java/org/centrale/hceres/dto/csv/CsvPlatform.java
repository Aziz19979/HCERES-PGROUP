package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.Platform;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvPlatform extends DependentCsv<Activity, Integer> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvPlatform;
    private static final int ID_CSV_PLATFORM_ORDER = 0;

    private java.sql.Date creationDate;
    private static final int CREATION_DATE_ORDER = 1;
    private String description;
    private static final int DESCRIPTION_ORDER = 2;
    private String managers;
    private static final int MANAGERS_ORDER = 3;
    private String affiliation;
    private static final int AFFILIATION_ORDER = 4;
    private String labellisation;
    private static final int LABELLISATION_ORDER = 5;
    private Boolean openPrivateResearchers;
    private static final int OPEN_PRIVATE_RESEARCHERS_ORDER = 6;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvPlatform(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_PLATFORM_ORDER,
                        f -> this.setIdCsvPlatform(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(CREATION_DATE_ORDER,
                        f -> this.setCreationDate(RequestParser.getAsDateCsvFormat(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(DESCRIPTION_ORDER,
                        f -> this.setDescription(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(MANAGERS_ORDER,
                        f -> this.setManagers(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(AFFILIATION_ORDER,
                        f -> this.setAffiliation(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(LABELLISATION_ORDER,
                        f -> this.setLabellisation(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(OPEN_PRIVATE_RESEARCHERS_ORDER,
                        f -> this.setOpenPrivateResearchers(RequestParser.getAsBoolean(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_PLATFORM_ORDER,
                        this.getIdCsvPlatform(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvPlatform()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.PLATFORM.getId());
        Platform platform = new Platform();
        platform.setCreationDate(this.getCreationDate());
        platform.setDescription(this.getDescription());
        platform.setManagers(this.getManagers());
        platform.setAffiliation(this.getAffiliation());
        platform.setLabellisation(this.getLabellisation());
        platform.setOpenPrivateResearchers(this.getOpenPrivateResearchers());
        activity.setPlatform(platform);
        platform.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getCreationDate()
                + "_" + this.getDescription()
                + "_" + this.getManagers()
                + "_" + this.getAffiliation()
                + "_" + this.getLabellisation()
                + "_" + this.getOpenPrivateResearchers()).toLowerCase();
    }
    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getPlatform().getCreationDate()
                + "_" + entity.getPlatform().getDescription()
                + "_" + entity.getPlatform().getManagers()
                + "_" + entity.getPlatform().getAffiliation()
                + "_" + entity.getPlatform().getLabellisation()
                + "_" + entity.getPlatform().getOpenPrivateResearchers()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvPlatform();
    }
}
