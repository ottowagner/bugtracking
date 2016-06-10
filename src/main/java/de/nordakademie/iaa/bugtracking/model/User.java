package de.nordakademie.iaa.bugtracking.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User entity.
 */
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 3185274412265195568L;

    /**
     * id
     */
    private Long id;
    /**
     * email
     */
    private String email;
    /**
     * firstname
     */
    private String firstname;
    /**
     * lastname
     */
    private String lastname;
    /**
     * password
     */
    private String password;
    /**
     * role
     */
    private String role = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NaturalId
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

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
