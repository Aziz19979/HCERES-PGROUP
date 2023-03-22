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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "tool_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolProduct implements Serializable {

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
    @Column(name = "tool_product_name")
    private String toolProductName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tool_product_creation")
    @Temporal(TemporalType.DATE)
    private Date toolProductCreation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tool_product_authors")
    private String toolProductAuthors;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tool_product_description")
    private String toolProductDescription;

    @Column(name = "tool_product_type_id")
    private Integer toolProductTypeId;

    @JsonIgnore
    @JoinColumn(name = "tool_product_type_id", referencedColumnName = "tool_product_type_id", insertable = false, updatable = false)
    @ManyToOne
    private ToolProductType toolProductType;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toolProduct")
    private List<ToolProductInvolvement> toolProductInvolvementList;
}