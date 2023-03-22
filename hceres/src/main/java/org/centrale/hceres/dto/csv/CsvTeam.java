package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Laboratory;
import org.centrale.hceres.items.Team;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvTeam extends DependentCsv<Team, Integer> {
    private Integer idTeamCsv;
    private static final int ID_TEAM_CSV_ORDER = 0;
    private String teamName;
    private static final int TEAM_NAME_ORDER = 1;
    private Integer laboratoryIdCsv;
    private static final int LABORATORY_ID_CSV_ORDER = 2;

    private GenericCsv<Laboratory, Integer> csvLaboratory;
    private final Map<Integer, GenericCsv<Laboratory, Integer>> laboratoryIdCsvMap;

    public CsvTeam(Map<Integer, GenericCsv<Laboratory, Integer>> laboratoryIdCsvMap) {
        this.laboratoryIdCsvMap = laboratoryIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_TEAM_CSV_ORDER,
                        f -> this.setIdTeamCsv(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(TEAM_NAME_ORDER,
                        f -> this.setTeamName(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(LABORATORY_ID_CSV_ORDER,
                        f -> this.setLaboratoryIdCsv(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(LABORATORY_ID_CSV_ORDER,
                        this.getLaboratoryIdCsv(),
                        SupportedCsvTemplate.LABORATORY,
                        this.laboratoryIdCsvMap.get(this.getLaboratoryIdCsv()),
                        this::setCsvLaboratory)
        );
    }

    @Override
    public Team convertToEntity() {
        Team team = new Team();
        team.setTeamName(this.getTeamName());
        team.setLaboratoryId(this.getCsvLaboratory().getIdDatabase());
        return team;
    }

    @Override
    public String getMergingKey() {
        return (this.getTeamName()
                + "_" + this.getCsvLaboratory().getIdDatabase())
                .toLowerCase();
    }

    @Override
    public String getMergingKey(Team entity) {
        return (entity.getTeamName()
                + "_" + entity.getLaboratoryId())
                .toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Team entity) {
        setIdDatabase(entity.getTeamId());
    }

    @Override
    public Integer getIdCsv() {
        return this.idTeamCsv;
    }

}
