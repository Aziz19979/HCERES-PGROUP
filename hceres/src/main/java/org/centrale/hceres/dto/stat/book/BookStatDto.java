package org.centrale.hceres.dto.stat.book;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.centrale.hceres.dto.stat.utils.ActivityStatDto;
import org.centrale.hceres.items.Activity;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class BookStatDto extends ActivityStatDto {
    private Date publicationDate;

    @Override
    public void fillDataFromActivity(Activity activity) {
        super.fillDataFromActivity(activity);
        this.publicationDate = activity.getBook().getPublicationDate();
    }
}
