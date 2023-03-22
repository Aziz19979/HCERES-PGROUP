/* --------------------------------------------------------------------------------
 * Projet HCERES
 * 
 * Gestion de données pour l'HCERES
 * 
 * Ecole Centrale Nantes - laboratoire CRTI
 * Avril 2021
 * L LETERTRE, S LIMOUX, JY MARTIN
 * -------------------------------------------------------------------------------- */
package org.centrale.hceres.items;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import org.centrale.tools.Utilities;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "researcher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Researcher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "researcher_id")
    private Integer researcherId;

    public Researcher(Integer researcherId) {
        this.researcherId = researcherId;
    }

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "researcher_surname")
    private String researcherSurname;
    
    @Size(max = 256)
    @Column(name = "researcher_name")
    private String researcherName;
    
    @Size(max = 256)
    @Column(name = "researcher_email")
    private String researcherEmail;
    
    @Size(max = 256)
    @Column(name = "researcher_orcid")
    private String researcherOrcid;
    
    @Size(max = 256)
    @Column(name = "researcher_login")
    private String researcherLogin;
    
    @Size(max = 1024)
    @Column(name = "researcher_password")
    private String researcherPassword;
    @JsonIgnore
    @ManyToMany(mappedBy = "researcherList")
    private List<Nationality> nationalityList;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "researcherList")
    private List<Activity> activityList;
    @JsonIgnore
    @OneToMany(mappedBy = "researcherId")
    private List<Connection> connectionList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "researcher")
    private List<Contract> contractList;

    // this is a dummy field just to return the last status of the researcher
    @Transient
    private String lastResearcherStatus;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "researcherId")
    private List<TeamReferent> teamReferentList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "researcherId")
    private List<Supervisor> supervisorList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "researcherId")
    private List<PhdStudent> phdStudentList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "researcher")
    private Admin admin;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "researcher")
    private List<BelongsTeam> belongsTeamList;
}