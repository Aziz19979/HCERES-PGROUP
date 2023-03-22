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
import javax.validation.constraints.Size;

/**
 * @author kwyhr
 */
@Entity
@Table(name = "type_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_type_activity")
    private Integer idTypeActivity;

    @Size(max = 256)
    @Column(name = "name_type")
    private String nameType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeActivity")
    @JsonIgnore
    private List<Activity> activityList;
}