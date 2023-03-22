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
import javax.persistence.CascadeType;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "network")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Network implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Size(max = 256)
    @Column(name = "name_network")
    private String nameNetwork;
    @Column(name = "active_network")
    private Boolean activeNetwork;
    @Size(max = 256)
    @Column(name = "associated_funding")
    private String associatedFunding;
    @Column(name = "nb_resulting_publications")
    private Integer nbResultingPublications;
    @Size(max = 256)
    @Column(name = "ref_resulting_publications")
    private String refResultingPublications;
    @Column(name = "umr_coordinated")
    private Boolean umrCoordinated;
    @Column(name = "agreement_signed")
    private Boolean agreementSigned;

}