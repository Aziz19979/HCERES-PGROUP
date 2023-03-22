package org.centrale.hceres.service;

import java.text.ParseException;
import java.util.*;

import org.centrale.hceres.items.Activity;
import org.centrale.hceres.items.SrAward;
import org.centrale.hceres.items.Researcher;
import org.centrale.hceres.items.TypeActivityId;
import org.centrale.hceres.repository.ActivityRepository;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;

// permet de traiter la requete HTTP puis l'associer a la fonction de repository qui va donner une reponse
@Data
@Service
public class SrAwardService {


    @Autowired
    private ActivityRepository activityRepo;

    /**
     * permet de retourner la liste
     */
    public List<Activity> getSrAwards() {
        return activityRepo.findByIdTypeActivity(TypeActivityId.SR_AWARD.getId());
    }

    /**
     * supprimer l'elmt selon son id
     *
     * @param id : id de l'elmt
     */
    public void deleteSrAward(final Integer id) {
        activityRepo.deleteById(id);
    }

    /**
     * permet d'ajouter un elmt
     *
     * @return : l'elemt ajouter a la base de donnees
     */
    public Activity saveSrAward(@RequestBody Map<String, Object> request) throws RequestParseException {

        SrAward srAward = new SrAward();

        // SrAwardCourseName :
        srAward.setAwardeeName(RequestParser.getAsString(request.get("awardeeName")));

        // SrAwardCompletion :
        srAward.setAwardDate(RequestParser.getAsDate(request.get("awardDate")));

        // SrAwardDescription :
        srAward.setDescription(RequestParser.getAsString(request.get("description")));

        // Activity :
        Activity activity = new Activity();
        srAward.setActivity(activity);
        activity.setSrAward(srAward);
        activity.setIdTypeActivity(TypeActivityId.SR_AWARD.getId());

        // get list of researcher doing this activity - currently only one is sent
        activity.setResearcherList(Collections.singletonList(new Researcher(RequestParser.getAsInteger(request.get("researcherId")))));

        activity = activityRepo.save(activity);
        return activity;
    }

}













