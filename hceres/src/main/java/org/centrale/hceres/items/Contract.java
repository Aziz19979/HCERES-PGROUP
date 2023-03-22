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
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contract")
    private Integer contractId;

    @Column(name = "start_contract")
    @Temporal(TemporalType.DATE)
    private Date startContract;
    @Column(name = "end_contract")
    @Temporal(TemporalType.DATE)
    private Date endContract;
    @Size(max = 256)
    @Column(name = "function_contract")
    private String functionContract;


    @Column(name = "id_contract_type")
    private Integer contractTypeId;

    @JoinColumn(name = "id_contract_type", referencedColumnName = "id_contract_type",
            insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ContractType contractType;
    @JoinColumn(name = "id_employer", referencedColumnName = "id_employer",
            insertable = true, updatable = true)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Employer employer;

    @Column(name = "researcher_id")
    private Integer researcherId;

    @JsonIgnore
    @JoinColumn(name = "researcher_id", referencedColumnName = "researcher_id",
            insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Researcher researcher;


    @Column(name = "id_status")
    private Integer statusId;

    @JoinColumn(name = "id_status", referencedColumnName = "id_status",
            insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Status status;
}