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
public class State implements Serializable {
    private static final long serialVersionUID = 6925248180274139277L;

    private Long id;
    private Long fromState;
    private String title;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = true)
    public Long getFromState() {
        return fromState;
    }

    public void setFromState(Long fromState) {
        this.fromState = fromState;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
