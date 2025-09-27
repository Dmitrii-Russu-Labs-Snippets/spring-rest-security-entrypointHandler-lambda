package com.example.demo_rest_2.security.entity;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public String getFirstName() { return firstName;}
    public void setFirstName(String firstName) { this.firstName = firstName;}

    public String getLastName() { return lastName;}
    public void setLastName(String lastName) { this.lastName = lastName;}

    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}

    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password;}

    public boolean isEnabled() { return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled;}

    public Set<Role> getRoles() { return roles;}
    public void setRoles(Set<Role> roles) { this.roles = roles;}

}


