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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * @author kwyhr
 */
@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_activity")
    private Integer idActivity;

    @Column(name = "id_type_activity")
    private Integer idTypeActivity;

    @JoinColumn(name = "id_type_activity", referencedColumnName = "id_type_activity", insertable = false, updatable = false)
    @ManyToOne
    private TypeActivity typeActivity;

    public void setTypeActivity(TypeActivity typeActivity) {
        this.typeActivity = typeActivity;
        if (typeActivity != null) setIdTypeActivity(typeActivity.getIdTypeActivity());
    }

    @JoinTable(name = "activity_researcher", joinColumns = {
            @JoinColumn(name = "id_activity", referencedColumnName = "id_activity")}, inverseJoinColumns = {
            @JoinColumn(name = "researcher_id", referencedColumnName = "researcher_id")})
    @ManyToMany
    @JsonManagedReference
    private List<Researcher> researcherList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private IncomingMobility incomingMobility;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private Education education;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivity")
    @JsonIgnore
    private List<MailActivity> mailActivityList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private InternationalCollaboration internationalCollaboration;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private Publication publication;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private ScientificExpertise scientificExpertise;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private InvolvementTrainingPedagogical involvementTrainingPedagogical;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private CompanyCreation companyCreation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private LabcomCreation labcomCreation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private SrAward srAward;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private PublicOutreach publicOutreach;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private ReviewArticle ReviewArticle;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private EvaluationThesis evaluationThesis;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private SeiNetworkUnitCreation seiNetworkUnitCreation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private ToolProduct toolProduct;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private PostDoc postDoc;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private Patent patent;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private Book book;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private InvitedSeminar invitedSeminar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private MeetingCongressOrg meetingCongressOrg;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private BookChapter bookChapter;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private Platform platform;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private Network network;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private InstitutionalComitee institutionalComitee;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private OutgoingMobility outgoingMobility;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private EditorialActivity editorialActivity;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private EducationalOutput educationalOutput;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private SeiIndustrialRDContract seiIndustrialRDContract;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private ProjectEvaluation projectEvaluation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private LaboratoryEvaluation laboratoryEvaluation;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private SeiClinicalTrial seiClinicalTrial;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    private OralComPoster oralComPoster;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private ResearchContractFundedCharit researchContractFundedCharit;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private SeiLeadConsortiumIndustry seiLeadConsortiumIndustry;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private SeiCifreFellowship seiCifreFellowship;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "activity")
    @JsonIgnore
    private LearnedScientificSociety learnedScientificSociety;
}
