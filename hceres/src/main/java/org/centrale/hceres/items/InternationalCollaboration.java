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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "national_international_collaboration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternationalCollaboration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    
    @Column(name = "date_project_start")
    @Temporal(TemporalType.DATE)
    private Date dateProjectStart;
    
    @Size(max = 256)
    @Column(name = "partner_entity")
    private String partnerEntity;
    
    @Size(max = 256)
    @Column(name = "country_state_city")
    private String countryStateCity;
    
    @Size(max = 256)
    @Column(name = "pi_partners")
    private String piPartners;
    
    @Size(max = 256)
    @Column(name = "mail_partners")
    private String mailPartners;
    
    @Size(max = 256)
    @Column(name = "projetc_title")
    private String projectTitle;
    
    @Column(name = "strategic_recurring_collab")
    private Boolean strategicRecurringCollab;
    
    @Column(name = "active_project")
    private Boolean activeProject;
    
    @Size(max = 256)
    @Column(name = "associated_funding")
    private String associatedFunding;
    
    @Column(name = "number_resulting_publications")
    private Integer numberResultingPublications;
    
    @Size(max = 256)
    @Column(name = "ref_joint_publication")
    private String refJointPublication;
    
    @Column(name = "umr_coordinated")
    private Boolean umrCoordinated;
    
    @Column(name = "agreement_signed")
    private Boolean agreementSigned;

    @Column(name = "type_collab_id")
    private Integer typeCollabId;

    @JsonIgnore
    @JoinColumn(
            name = "type_collab_id",
            referencedColumnName = "type_collab_id",
            insertable = false,
            updatable = false
    )
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private TypeCollab typeCollab;
}