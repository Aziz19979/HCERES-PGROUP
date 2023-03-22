package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.*;
import org.centrale.hceres.items.Institution;
import org.centrale.hceres.items.Laboratory;
import org.centrale.hceres.service.csv.util.SupportedCsvTemplate;
import org.centrale.hceres.util.RequestParser;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvLaboratory extends DependentCsv<Laboratory, Integer> {
    private Integer idCsv;
    private static final int ID_CSV_ORDER = 0;

    private String laboratoryName;
    private static final int LABORATORY_NAME_ORDER = 1;
    private String laboratoryAcronym;
    private static final int LABORATORY_ACRONYM_ORDER = 2;
    /**
     * Id of the institution read from the csv,
     * used to initialize the csvInstitution object using the institutionIdCsvMap
     */
    private Integer institutionIdCsv;
    private static final int INSTITUTION_ID_CSV_ORDER = 3;

    private GenericCsv<Institution, Integer> csvInstitution;
    private final Map<Integer, GenericCsv<Institution, Integer>> institutionIdCsvMap;

    /**
     * Constructor
     *
     * @param institutionIdCsvMap Map from institution id to csvInstitution
     */
    public CsvLaboratory(Map<Integer, GenericCsv<Institution, Integer>> institutionIdCsvMap) {
        this.institutionIdCsvMap = institutionIdCsvMap;
    }

    @Override
    public void fillCsvDataWithoutDependency(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORDER,
                        f -> this.setIdCsv(RequestParser.getAsInteger(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(LABORATORY_NAME_ORDER,
                        f -> this.setLaboratoryName(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(LABORATORY_ACRONYM_ORDER,
                        f -> this.setLaboratoryAcronym(RequestParser.getAsString(csvData.get(f)))),

                () -> CsvParserUtil.wrapCsvParseException(INSTITUTION_ID_CSV_ORDER,
                        f -> this.setInstitutionIdCsv(RequestParser.getAsInteger(csvData.get(f))))
        );
    }

    @Override
    public void initializeDependencies() throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvDependencyException(INSTITUTION_ID_CSV_ORDER,
                        this.getInstitutionIdCsv(),
                        SupportedCsvTemplate.INSTITUTION,
                        this.institutionIdCsvMap.get(this.getInstitutionIdCsv()),
                        this::setCsvInstitution)
        );
    }

    @Override
    public void setIdDatabaseFromEntity(Laboratory entity) {
        setIdDatabase(entity.getLaboratoryId());
    }

    @Override
    public Integer getIdCsv() {
        return this.idCsv;
    }

    @Override
    public Laboratory convertToEntity() {
        Laboratory laboratory = new Laboratory();
        laboratory.setLaboratoryName(this.getLaboratoryName());
        laboratory.setLaboratoryAcronym(this.getLaboratoryAcronym());
        laboratory.setInstitutionId(this.getCsvInstitution().getIdDatabase());
        return laboratory;
    }

    @Override
    public String getMergingKey() {
        return (this.getLaboratoryName()
                + "_" + this.getLaboratoryAcronym()
                + "_" + this.getCsvInstitution().getIdDatabase())
                .toLowerCase();
    }

    @Override
    public String getMergingKey(Laboratory entity) {
        return (entity.getLaboratoryName()
                + "_" + entity.getLaboratoryAcronym()
                + "_" + entity.getInstitutionId())
                .toLowerCase();
    }
}
