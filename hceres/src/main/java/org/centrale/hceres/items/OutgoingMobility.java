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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "outgoing_mobility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingMobility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    @Size(max = 256)
    @Column(name = "name_person_concerned")
    private String namePersonConcerned;
    @Column(name = "arrival_date")
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;
    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departureDate;
    @Column(name = "duration")
    private Integer duration;
    @Size(max = 256)
    @Column(name = "host_lab_name")
    private String hostLabName;
    @Size(max = 256)
    @Column(name = "host_lab_location")
    private String hostLabLocation;
    @Size(max = 256)
    @Column(name = "pi_partner")
    private String piPartner;
    @Size(max = 256)
    @Column(name = "project_title")
    private String projectTitle;
    @Size(max = 256)
    @Column(name = "associated_funding")
    private String associatedFunding;
    @Column(name = "nb_publications")
    private Integer nbPublications;
    @Size(max = 256)
    @Column(name = "publication_reference")
    private String publicationReference;
    @Column(name = "strategic_recurring_collab")
    private Boolean strategicRecurringCollab;
    @Column(name = "active_project")
    private Boolean activeProject;
    @Column(name = "umr_coordinated")
    private Boolean umrCoordinated;
    @Column(name = "agreement_signed")
    private Boolean agreementSigned;

}