package org.centrale.hceres.dto.stat.utils;


import lombok.Data;
import org.centrale.hceres.items.Activity;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
public class ActivityStatDto implements Serializable, StatItemDto {
    private int idActivity;
    private SortedSet<Integer> teamIds;

    public ActivityStatDto() {
        this.teamIds = new TreeSet<>();
    }

    public void fillDataFromActivity(Activity activity) {
        this.idActivity = activity.getIdActivity();
        activity.getResearcherList().forEach(researcher ->
                researcher.getBelongsTeamList().forEach(belongsTeams ->
                        this.teamIds.add(belongsTeams.getTeamId())));
    }
}
