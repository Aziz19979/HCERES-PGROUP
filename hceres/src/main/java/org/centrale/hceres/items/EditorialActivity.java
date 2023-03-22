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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "editorial_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditorialActivity implements Serializable {
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
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "impact_factor")
    private BigDecimal impactFactor;


    @Column(name = "function_editorial_activity_id")
    private Integer functionEditorialActivityId;

    @JsonIgnore
    @JoinColumn(
            name = "function_editorial_activity_id",
            referencedColumnName = "function_editorial_activity_id",
            insertable = false,
            updatable = false
    )
    @ManyToOne(optional = false)
    private FunctionEditorialActivity functionEditorialActivity;


    @Column(name = "journal_id")
    private Integer journalId;

    @JoinColumn(
            name = "journal_id",
            referencedColumnName = "journal_id",
            insertable = false,
            updatable = false
    )
    @ManyToOne(optional = false)
    private Journal journal;
}