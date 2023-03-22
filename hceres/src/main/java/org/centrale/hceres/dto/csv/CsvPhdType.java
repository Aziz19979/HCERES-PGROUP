package org.centrale.hceres.dto.csv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.csv.utils.CsvAllFieldExceptions;
import org.centrale.hceres.dto.csv.utils.CsvParseFieldException;
import org.centrale.hceres.dto.csv.utils.CsvParserUtil;
import org.centrale.hceres.dto.csv.utils.InDependentCsv;
import org.centrale.hceres.items.PhdType;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CsvPhdType extends InDependentCsv<PhdType, Integer> {
    private String phdTypeName;
    private static final int PHD_TYPE_NAME_ORDER = 1;

    @Override
    public void fillCsvData(List<?> csvData) throws CsvAllFieldExceptions {
        CsvParserUtil.wrapCsvAllFieldExceptions(
                () -> CsvParserUtil.wrapCsvParseException(ID_CSV_ORDER,
                        f -> this.setIdCsv(RequestParser.getAsInteger(csvData.get(f)))),
                () -> CsvParserUtil.wrapCsvParseException(PHD_TYPE_NAME_ORDER,
                        f -> this.setPhdTypeName(RequestParser.getAsString(csvData.get(f))))
        );
    }

    @Override
    public PhdType convertToEntity() {
        PhdType phdType = new PhdType();
        phdType.setPhdTypeName(this.getPhdTypeName());
        return phdType;
    }

    @Override
    public String getMergingKey() {
        return (this.getPhdTypeName())
                .toLowerCase();
    }

    @Override
    public String getMergingKey(PhdType phdType) {
        return (phdType.getPhdTypeName())
                .toLowerCase();
    }

    @Override
    public void setIdDatabaseFromEntity(PhdType phdType) {
        setIdDatabase(phdType.getPhdTypeId());
    }
}
