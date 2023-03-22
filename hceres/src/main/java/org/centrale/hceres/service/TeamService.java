package org.centrale.hceres.service;

import java.util.*;

import org.centrale.hceres.items.Team;
import org.centrale.hceres.repository.TeamRepository;
import org.centrale.hceres.util.RequestParseException;
import org.centrale.hceres.util.RequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;

// permet de traiter la requete HTTP puis l'associer a la fonction de repository qui va donner une reponse
@Data
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    public List<Team> getTeams() {
        return teamRepo.findAll();
    }
    /**
     * supprimer l'elmt selon son id
     *
     * @param id : id de l'elmt
     */
    public void deleteTeam(final Integer id) {
        teamRepo.deleteById(id);
    }

    /**
     * permet d'ajouter un elmt
     *
     * @return : l'elemt ajouter a la base de donnees
     */
    public Team saveTeam(@RequestBody Map<String, Object> request) throws RequestParseException {

        Team team = new Team();
        team.setTeamName(RequestParser.getAsString(request.get("teamName")));
        team.setTeamCreation(RequestParser.getAsDate(request.get("teamCreation")));
        team.setTeamEnd(RequestParser.getAsDate(request.get("teamEnd")));
        team.setTeamLastReport(RequestParser.getAsDate(request.get("teamLastReport")));
        team.setLaboratoryId(RequestParser.getAsInteger(request.get("laboratoryId")));
        teamRepo.save(team);
        return team;
    }

}
