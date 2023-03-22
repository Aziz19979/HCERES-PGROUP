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
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "learned_scientific_society")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearnedScientificSociety implements Serializable {

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
    @Column(name = "learned_scientific_society_name")
    private String learnedScientificSocietyName;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;


    @Column(name = "learned_scientific_society_role_id")
    private Integer learnedScientificSocietyRoleId;

    @JoinColumn(
            name = "learned_scientific_society_role_id",
            referencedColumnName = "learned_scientific_society_role_id",
            insertable = false,
            updatable = false
    )
    @ManyToOne(optional = false)
    private LearnedScientificSocietyRole learnedScientificSocietyRole;
}