package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Bug entity.
 */
@Entity
public class Bug implements Serializable {
    private static final long serialVersionUID = -2505362905947838530L;
    /**
     * unique identifier
     */
    private Long id;
    /**
     * title
     */
    private String title;
    /**
     * description
     */
    private String description;
    /**
     * state of the bug
     */
    private State state;
    /**
     * the author
     */
    private User author;
    /**
     * the developer
     */
    private User developer;
    /**
     * lastUpdateDate
     */
    private Date lastUpdateDate;
    /**
     * creationDate
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

    @Column(nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ManyToOne(optional = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User autor) {
        this.author = autor;
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
