package org.centrale.hceres.dto.stat.publication;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.stat.utils.ActivityStatSumDto;
import org.centrale.hceres.items.PublicationType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PublicationStatSumDto extends ActivityStatSumDto {
    private List<PublicationType> publicationTypes;
}
