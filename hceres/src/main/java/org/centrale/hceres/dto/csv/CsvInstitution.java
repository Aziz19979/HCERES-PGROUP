package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.CsvAllFieldExceptions;
import org.centrale.hceres.dto.csv.utils.CsvParserUtil;
import org.centrale.hceres.dto.csv.utils.InDependentCsv;
import org.centrale.hceres.items.Institution;
import org.centrale.hceres.util.RequestParser;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvInstitution extends InDependentCsv<Institution, Integer> {
    private String institutionName;
    private static final int INSTITUTION_NAME_ORDER = 1;

    @Override
    public void fillCsvData(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORDER,
                        f -> this.setIdCsv(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(INSTITUTION_NAME_ORDER,
                        f -> this.setInstitutionName(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public Institution convertToEntity() {
        Institution institution = new Institution();
        institution.setInstitutionName(this.getInstitutionName());
        return institution;
    }

    @Override
    public String getMergingKey() {
        return (this.getInstitutionName())
                .toLowerCase();
    }

    @Override
    public String getMergingKey(Institution institution) {
        return (institution.getInstitutionName())
                .toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(Institution institution) {
        setIdDatabase(institution.getInstitutionId());
    }
}
