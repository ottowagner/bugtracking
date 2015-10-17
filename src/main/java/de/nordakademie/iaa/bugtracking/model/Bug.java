package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Bug entity.
 *
 * @author Otto Wagner
 */
@Entity
public class Bug implements Serializable {
    private static final long serialVersionUID = 6925248180274039277L;

    /**
     * The unique identifier.
     */
    private Long id;

    /**
     * The title.
     */
    private String title;

    /**
     * The description.
     */
    private String description;

    //TODO: ENUM
    /**
     * The state.
     */
    private String state;

    /**
     * The autor.
     */
    private User autor;

    /**
     * The developer.
     */
    private User developer;

    //TODO: Datum muss Typ DATUM sein! oder timestamp... wie auch immer.. :P
    /**
     * The lastUpdateDate.
     */
    private Date lastUpdateDate;

    //TODO: Datum muss Typ DATUM sein!
    /**
     * The creationDate.
     */
    private Date creationDate;

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

    @Column(nullable = true, length = 4000)
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

    @ManyToOne(optional = false)
    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    @ManyToOne(optional = true)
    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
