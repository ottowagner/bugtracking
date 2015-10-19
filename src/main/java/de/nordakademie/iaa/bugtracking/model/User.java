package de.nordakademie.iaa.bugtracking.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User entity.
 *
 * @author Otto Wagner
 */
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL"}))
public class User implements Serializable {
    private static final long serialVersionUID = 6925248180274039274L;

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
