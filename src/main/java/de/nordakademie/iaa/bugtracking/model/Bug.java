package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Bug entity.
 *
 * @author Otto Wagner
 */
@Entity
public class Bug implements Serializable {
    private static final long serialVersionUID = 6925248180274039277L;

    /** The unique identifier. */
    private Long id;

    /** The title. */
    private String title;

    /** The description. */
    private String description;

    //TODO: ENUM
    /** The state. */
    private String state;

    //TODO: Verknüpfung mit User
    /** The autor. */
    private Long autor;

    //TODO: Verknüpfung mit User
    /** The developer. */
    private Long developer;

    //TODO: Datum muss Typ DATUM sein! oder timestamp... wie auch immer.. :P
    /** The lastUpdateDate. */
    private String lastUpdateDate;

    //TODO: Datum muss Typ DATUM sein!
    /** The creationDate. */
    private String creationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(nullable = false)
    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    @Column(nullable = true)
    public Long getDeveloper() {
        return developer;
    }

    public void setDeveloper(Long developer) {
        this.developer = developer;
    }

    @Column(nullable = true)
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(nullable = false)
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}
