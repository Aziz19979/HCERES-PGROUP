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
@Table(name = "book_chapter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookChapter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_activity")
    private Integer idActivity;

    @JsonIgnore
    @JoinColumn(name = "id_activity")
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    private Activity activity;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Size(max = 256)
    @Column(name = "book_title")
    private String bookTitle;
    @Size(max = 256)
    @Column(name = "chapter_title")
    private String chapterTitle;
    @Size(max = 256)
    @Column(name = "editor")
    private String editor;
    @Column(name = "nb_page")
    private Integer nbPage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "authors")
    private String authors;
    @Size(max = 2147483647)
    @Column(name = "additional_info")
    private String additionalInfo;

    @JoinColumn(name = "language_id", referencedColumnName = "language_id")
    @ManyToOne(optional = false)
    private Language languageId;
}