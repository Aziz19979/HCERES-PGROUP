package org.centrale.hceres.service.stat;

import lombok.Data;
import org.centrale.hceres.dto.stat.publication.PublicationStatDto;
import org.centrale.hceres.dto.stat.publication.PublicationStatSumDto;
import org.centrale.hceres.dto.stat.utils.ActivityStatDto;
import org.centrale.hceres.dto.stat.utils.ActivityStatSumDto;
import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.PublicationType;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ActivityStatService {

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private PublicationStatService publicationStatService;

    @Autowired
    private BookStatService bookStatService;

    public ActivityStatSumDto getStatByTypeActivity(Integer idTypeActivity) {
        ActivityStatSumDto activityStatSumDto = createStatSumActivity(idTypeActivity);

        List<ActivityStatDto> activityStatDtoList = new ArrayList<>();
        activityRepo.findByIdTypeActivity(idTypeActivity).forEach(activity ->
                activityStatDtoList.add(createStatActivity(activity)));

        activityStatSumDto.setItems(activityStatDtoList);

        return activityStatSumDto;
    }

    private ActivityStatSumDto createStatSumActivity(Integer idTypeActivity) {
        TypeActivityId typeActivityId = TypeActivityId.fromId(idTypeActivity);
        switch (typeActivityId) {
            case PUBLICATION:
                return publicationStatService.createStatSumPublication();
            case BOOK:
            default:
                return new ActivityStatSumDto();
        }
    }

    private ActivityStatDto createStatActivity(Activity activity) {
        ActivityStatDto activityStatDto;
        TypeActivityId typeActivityId = TypeActivityId.fromId(activity.getIdTypeActivity());
        switch (typeActivityId) {
            case PUBLICATION:
                activityStatDto = publicationStatService.createStatPublication(activity);
                break;
            case BOOK:
                activityStatDto = bookStatService.createStatBook(activity);
                break;
            default:
                activityStatDto = new ActivityStatDto();
                activityStatDto.fillDataFromActivity(activity);
                break;
        }
        return activityStatDto;
    }
}
