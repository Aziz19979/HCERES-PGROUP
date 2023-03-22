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
@Table(name = "research_contract_funded_public_charitable_inst")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchContractFundedCharit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    @Column(name = "date_contract_award")
    @Temporal(TemporalType.DATE)
    private Date dateContractAward;
    @Size(max = 256)
    @Column(name = "funding_institution")
    private String fundingInstitution;
    @Size(max = 256)
    @Column(name = "project_title")
    private String projectTitle;
    @Column(name = "start_year")
    private Integer startYear;
    @Column(name = "end_year")
    private Integer endYear;
    @Column(name = "grant_amount")
    private Integer grantAmount;


    @Column(name = "id_type")
    private Integer typeResearchContractId;

    @JoinColumn(
            name = "id_type",
            referencedColumnName = "id_type",
            insertable = false,
            updatable = false
    )
    @ManyToOne(optional = false)
    private TypeResearchContract typeResearchContract;
}