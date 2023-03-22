package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.PublicOutreach;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvPublicOutreach extends DependentCsv<Activity, Integer> {

    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvPublicOutreach;
    private static final int ID_CSV_PUBLIC_OUTREACH_ORDER = 0;

    private String description;
    private static final int DESCRIPTION_ORDER = 1;
    private Integer idType;
    private static final int ID_TYPE_ORDER = 2;

    // dependency element
    private CsvActivity csvActivity;
    private Map<Integer, CsvActivity> activityIdCsvMap;

    public CsvPublicOutreach(Map<Integer, CsvActivity> activityIdCsvMap) {
        this.activityIdCsvMap = activityIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_PUBLIC_OUTREACH_ORDER,
                        f -> this.setIdCsvPublicOutreach(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(DESCRIPTION_ORDER,
                        f -> this.setDescription(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TYPE_ORDER,
                        f -> this.setIdType(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_PUBLIC_OUTREACH_ORDER,
                        this.getIdCsvPublicOutreach(),
                        SupportedCsvTemplate.ACTIVITY,
                        this.activityIdCsvMap.get(this.getIdCsvPublicOutreach()),
                        this::setCsvActivity)
        );
    }

    @Override
    public Activity convertToEntity() {
        Activity activity = this.getCsvActivity().convertToEntity();
        activity.setIdTypeActivity(TypeActivityId.PUBLIC_OUTREACH.getId());
        PublicOutreach publicOutreach = new PublicOutreach();
        publicOutreach.setDescription(this.getDescription());
        // direct use of id present in database public_outreach_type
        publicOutreach.setPublicOutreachTypeId(this.getIdType());
        activity.setPublicOutreach(publicOutreach);
        publicOutreach.setActivity(activity);
        return activity;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvActivity().getCsvResearcher().getIdDatabase()
                + "_" + this.getDescription()
                + "_" + this.getIdType()).toLowerCase();
    }

    @Override
    public String getMergingKey(Activity entity) {
        return (entity.getResearcherList().get(0).getResearcherId()
                + "_" + entity.getPublicOutreach().getDescription()
                + "_" + entity.getPublicOutreach().getPublicOutreachTypeId()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Activity entity) {
        this.setIdDatabase(entity.getIdActivity());
    }

    @Override
    public Integer getIdCsv() {
        return this.getIdCsvPublicOutreach();
    }
}
