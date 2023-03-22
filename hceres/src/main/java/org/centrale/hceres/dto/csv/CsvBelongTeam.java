package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.BelongsTeam;
import org.centrale.hceres.items.Researcher;
import org.centrale.hceres.items.Team;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvBelongTeam extends DependentCsv<BelongsTeam, String> {
    // important the read field of name id_activity isn't the same
    // id activity in activity.csv
    // to get the id activity use both key:
    // the type of activity and the specific count
    private Integer idCsvResearcher;
    private static final int ID_CSV_RESEARCHER_ORDER = 0;
    private GenericCsv<Researcher, Integer> csvResearcher;
    private final Map<Integer, GenericCsv<Researcher, Integer>> researcherIdCsvMap;
    private Integer idTeamCsv;
    private static final int ID_TEAM_CSV_ORDER = 1;
    private GenericCsv<Team, Integer> csvTeam;
    private final Map<Integer, GenericCsv<Team, Integer>> teamIdCsvMap;

    public CsvBelongTeam(Map<Integer, GenericCsv<Researcher, Integer>> researcherIdCsvMap,
                         Map<Integer, GenericCsv<Team, Integer>> teamIdCsvMap) {
        this.researcherIdCsvMap = researcherIdCsvMap;
        this.teamIdCsvMap = teamIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_RESEARCHER_ORDER,
                        f -> this.setIdCsvResearcher(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(ID_TEAM_CSV_ORDER,
                        f -> this.setIdTeamCsv(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(ID_CSV_RESEARCHER_ORDER,
                        this.getIdCsvResearcher(),
                        SupportedCsvTemplate.RESEARCHER,
                        this.researcherIdCsvMap.get(this.getIdCsvResearcher()),
                        this::setCsvResearcher),

                () -> CsvParserUtil.wrapCsvDependencyException(ID_TEAM_CSV_ORDER,
                        this.getIdTeamCsv(),
                        SupportedCsvTemplate.TEAM,
                        this.teamIdCsvMap.get(this.getIdTeamCsv()),
                        this::setCsvTeam)
        );
    }

    @Override
    public BelongsTeam convertToEntity() {
        BelongsTeam belongsTeam = new BelongsTeam();
        belongsTeam.setResearcherId(this.csvResearcher.getIdDatabase());
        belongsTeam.setTeamId(this.csvTeam.getIdDatabase());
        return belongsTeam;
    }

    @Override
    public String getMergingKey() {
        return (this.getCsvResearcher().getIdDatabase()
                + "_" + this.getCsvTeam().getIdDatabase()).toLowerCase();
    }

    @Override
    public String getMergingKey(BelongsTeam entity) {
        return (entity.getResearcherId() + "_" + entity.getTeamId()).toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(BelongsTeam entity) {
        this.setIdDatabase(entity.getResearcherId() + "_" + entity.getTeamId());
    }

    @Override
    public String getIdCsv() {
        return this.getIdCsvResearcher() + "_" + this.getIdTeamCsv();
    }
}
