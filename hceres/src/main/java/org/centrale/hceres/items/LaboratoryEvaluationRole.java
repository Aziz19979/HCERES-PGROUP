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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "laboratory_evaluation_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryEvaluationRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "laboratory_evaluation_role_id")
    private Integer laboratoryEvaluationRoleId;
    @Size(max = 256)
    @Column(name = "name_choice")
    private String nameChoice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoryEvaluationRole")
    private List<InstitutionalComitee> institutionalComiteeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoryEvaluationRoleId")
    private List<LaboratoryEvaluation> laboratoryEvaluationList;
}