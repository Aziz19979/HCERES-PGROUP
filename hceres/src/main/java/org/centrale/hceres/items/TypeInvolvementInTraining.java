package org.centrale.hceres.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "type_involvement_in_training")
public class TypeInvolvementInTraining implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_type", nullable = false)
    private int idType;
    @Basic
    @Column(name = "name_choice", length = -1)
    private String nameChoice;

    @JsonIgnore
    @OneToMany(mappedBy = "typeInvolvementInTraining")
    private List<InvolvementTrainingPedagogical> involvementTrainingPedagogicalList;
}
