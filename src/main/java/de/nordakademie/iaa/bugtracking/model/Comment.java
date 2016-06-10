package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Comment entity.
 */
@Entity
public class Comment implements Serializable {
    private static final long serialVersionUID = 7758536242036634910L;
    /**
     * unique identifier
     */
    private Long id;
    /**
     * the bug related to this comment
     */
    private Bug bug;
    /**
     * title
     */
    private String title;
    /**
     * description
     */
    private String description;
    /**
     * author
     */
    private User author;
    /**
     * creationDate
     */
    private Date creationDate;
    /**
     * the state before a state change
     */
    private String fromState;
    /**
     * the state after a state change
     */
    private String toState;

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

    @Column(nullable = true)
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

    @ManyToOne(optional = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(nullable = true)
    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    @Column(nullable = true)
    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }
}