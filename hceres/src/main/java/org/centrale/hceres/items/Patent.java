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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "patent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patent implements Serializable {

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
    @Column(name = "title")
    private String title;
    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Column(name = "filing_date")
    @Temporal(TemporalType.DATE)
    private Date filingDate;
    @Column(name = "acceptation_date")
    @Temporal(TemporalType.DATE)
    private Date acceptationDate;
    @Column(name = "licensing_date")
    @Temporal(TemporalType.DATE)
    private Date licensingDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "inventors")
    private String inventors;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "co_owners")
    private String coOwners;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "priority_number")
    private Float priorityNumber;
    @Column(name = "priority_date")
    @Temporal(TemporalType.DATE)
    private Date priorityDate;
    @Size(max = 256)
    @Column(name = "publication_number")
    private String publicationNumber;
    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Size(max = 256)
    @Column(name = "inpi_link")
    private String inpiLink;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private boolean status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pct_extension_obtained")
    private boolean pctExtensionObtained;
    @Size(max = 256)
    @Column(name = "publication_number_pct_extension")
    private String publicationNumberPctExtension;
    @Column(name = "publication_date_pct_extension")
    @Temporal(TemporalType.DATE)
    private Date publicationDatePctExtension;
    @Basic(optional = false)
    @NotNull
    @Column(name = "international_extension")
    private boolean internationalExtension;
    @Size(max = 256)
    @Column(name = "publication_number_international_extension")
    private String publicationNumberInternationalExtension;
    @Column(name = "publication_date_international_extension")
    @Temporal(TemporalType.DATE)
    private Date publicationDateInternationalExtension;
    @Size(max = 256)
    @Column(name = "ref_transfer_contract")
    private String refTransferContract;
    @Size(max = 256)
    @Column(name = "name_company_involved")
    private String nameCompanyInvolved;

    @JoinColumn(name = "type_patent_id", referencedColumnName = "type_patent_id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private TypePatent typePatentId;
}