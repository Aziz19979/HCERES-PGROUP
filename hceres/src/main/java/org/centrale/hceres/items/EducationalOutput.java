package org.centrale.hceres.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "educational_output")
public class EducationalOutput implements Serializable {

    @Id
    @Column(name = "id_activity", nullable = false)
    private int idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    @Basic
    @Column(name = "id_type")
    private Integer idType;
    @Basic
    @Column(name = "completion_date")
    private Date completionDate;
    @Basic
    @Column(name = "description", length = -1)
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "id_type",
            referencedColumnName = "id_type",
            insertable = false,
            updatable = false
    )
    private TypeEducationalOutput typeEducationalOutput;
}
