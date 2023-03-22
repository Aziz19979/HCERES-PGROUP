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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "nationality")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nationality implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nationality_id")
    private Integer nationalityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nationality_name")
    private String nationalityName;

    @JsonIgnore
    @JoinTable(name = "researcher_nationality", joinColumns = {
        @JoinColumn(name = "nationality_id", referencedColumnName = "nationality_id")}, inverseJoinColumns = {
        @JoinColumn(name = "researcher_id", referencedColumnName = "researcher_id")})
    @ManyToMany
    private List<Researcher> researcherList;
}