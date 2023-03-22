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
@Table(name = "labcom_creation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabcomCreation implements Serializable {

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
    @Column(name = "labcom_creation_name")
    private String labcomCreationName;
    @Column(name = "contract_start_date")
    @Temporal(TemporalType.DATE)
    private Date contractStartDate;
    @Size(max = 256)
    @Column(name = "name_company_involved")
    private String nameCompanyInvolved;
    @Size(max = 256)
    @Column(name = "title_project")
    private String titleProject;
    @Column(name = "contract_end_date")
    @Temporal(TemporalType.DATE)
    private Date contractEndDate;
    @Size(max = 256)
    @Column(name = "associated_publi_ref")
    private String associatedPubliRef;
}