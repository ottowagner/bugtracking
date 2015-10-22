package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bug entity.
 *
 * @author Otto Wagner
 */
@Entity
public class Bug implements Serializable {
    private static final long serialVersionUID = 6925248180274039277L;

    private Long id;
    private String title;
    private String description;
    private State state;
    //http://websystique.com/hibernate/hibernate-many-to-many-unidirectional-annotation-example/
    private List<State> possibleStates = new ArrayList<State>();
    private User autor;
    private User developer;
    private Date lastUpdateDate;
    private Date creationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BUG_ID")
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

    @ManyToOne(optional = false)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BUG_STATE",
            joinColumns = {@JoinColumn(name = "BUG_ID")},
            inverseJoinColumns = {@JoinColumn(name = "STATE_ID")})
    public List<State> getPossibleStates() {
        return possibleStates;
    }

    public void setPossibleStates(List<State> possibleStates) {
        this.possibleStates = possibleStates;
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
