package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Comment entity.
 *
 * @author Otto Wagner
 */
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL"}))
public class Comment implements Serializable {
    private static final long serialVersionUID = 7758536242036634910L;

    private Long id;
    private Bug bug;
    private String title;
    private String description;
    private User autor;
    private Date creationDate;
    private String fromState;
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
    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
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