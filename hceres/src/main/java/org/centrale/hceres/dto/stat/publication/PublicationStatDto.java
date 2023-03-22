package org.centrale.hceres.dto.stat.publication;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.stat.utils.ActivityStatDto;
import org.centrale.hceres.items.Activity;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PublicationStatDto extends ActivityStatDto {
    private Date publicationDate;
    private Integer publicationTypeId;
    private BigDecimal impactFactor;

    @Override
    public void fillDataFromActivity(Activity activity) {
        super.fillDataFromActivity(activity);
        this.publicationDate = activity.getPublication().getPublicationDate();
        this.publicationTypeId = activity.getPublication().getPublicationTypeId();
        this.impactFactor = activity.getPublication().getImpactFactor();
    }
}
