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
    /**
     * The unique identifier.
     */
    private Long id;

    /**
     * The email.
     */
    private String email;

    /**
     * The firstName.
     */
    private String firstName;

    /**
     * The lastName.
     */
    private String lastName;

    /**
     * The password.
     */
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
