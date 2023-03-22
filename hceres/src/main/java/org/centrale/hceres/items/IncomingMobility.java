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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "incoming_mobility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMobility implements Serializable {

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
    @Column(name = "name_senior_scientist")
    private String nameSeniorScientist;
    @Column(name = "arrival_date")
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;
    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departureDate;
    @Column(name = "duration")
    private Integer duration;
    @Size(max = 256)
    @Column(name = "nationality")
    private String nationality;
    @Size(max = 256)
    @Column(name = "original_lab_name")
    private String originalLabName;
    @Size(max = 256)
    @Column(name = "origina_lab_location")
    private String originaLabLocation;
    @Size(max = 256)
    @Column(name = "pi_partner")
    private String piPartner;
    @Size(max = 256)
    @Column(name = "project_title")
    private String projectTitle;
    @Size(max = 256)
    @Column(name = "associated_funding")
    private String associatedFunding;
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