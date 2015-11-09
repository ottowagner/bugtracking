package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * State entity.
 *
 * @author Otto Wagner, Johan Ahrens
 */
@Entity
public class State implements Serializable {
    private static final long serialVersionUID = 8647209811200579375L;

    /**
     * unique identifier
     */
    private Long id;
    /**
     * title
     */
    private String title;
    /**
     * allowed toStates
     */
    private Set<Long> toStateId = new HashSet<Long>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATE_ID")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "STATE_TOSTATE", joinColumns = @JoinColumn(name = "STATE_ID"))
    @Column(name = "TOSTATE_ID")
    public Set<Long> getToStateId() {
        return toStateId;
    }

    public void setToStateId(Set<Long> toStateId) {
        this.toStateId = toStateId;
    }
}
