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
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education implements Serializable {

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
    @Column(name = "education_course_name")
    private String educationCourseName;
    
    @Column(name = "education_completion")
    @Temporal(TemporalType.DATE)
    private Date educationCompletion;
    
    @Size(max = 2147483647)
    @Column(name = "education_description")
    private String educationDescription;
    
    @Size(max = 256)
    @Column(name = "education_formation")
    private String educationFormation;

    @JsonIgnore
    @JoinColumn(name = "education_involvement_id", referencedColumnName = "education_involvement_id")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private EducationInvolvement educationInvolvementId;
    @JsonIgnore
    @JoinColumn(name = "education_level_id", referencedColumnName = "education_level_id")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private EducationLevel educationLevelId;
}