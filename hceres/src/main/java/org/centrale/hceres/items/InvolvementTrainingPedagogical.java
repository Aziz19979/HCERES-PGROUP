package org.centrale.hceres.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "involvement_training_pedagogical_responsibility")
public class InvolvementTrainingPedagogical implements Serializable {

    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Basic
    @Column(name = "name_master", length = -1)
    private String nameMaster;
    @Basic
    @Column(name = "id_type")
    private Integer idType;
    @ManyToOne
    @JoinColumn(
            name = "id_type",
            referencedColumnName = "id_type",
            insertable = false,
            updatable = false
    )
    private TypeInvolvementInTraining typeInvolvementInTraining;
}
