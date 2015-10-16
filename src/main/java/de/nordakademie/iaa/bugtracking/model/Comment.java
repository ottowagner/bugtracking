package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Comment entity.
 *
 * @author Otto Wagner
 */
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL"}))
public class Comment implements Serializable {
    private static final long serialVersionUID = 6925248180274039234L;
    /**
     * The unique identifier.
     */
    private Long id;

    /**
     * The bug which the comment belongs to.
     */
    private Bug bug;

    /**
     * The title.
     */
    private String title;

    /**
     * The description.
     */
    private String description;

    //TODO: Typ User
    /**
     * The autor.
     */
    private String autor;

    /**
     * The creationDate.
     */
    private String creationDate;

    //TODO: fromState, toState

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = true, length = 4000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Column(nullable = false)
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}