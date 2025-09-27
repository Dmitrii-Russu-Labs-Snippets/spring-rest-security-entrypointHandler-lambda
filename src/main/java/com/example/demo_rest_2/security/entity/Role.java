package com.example.demo_rest_2.security.entity;

import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private UserRole role;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + role.name());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role other)) return false;
        return role == other.role;
    }

    @Override
    public int hashCode() {
        return role != null ? role.hashCode() : 0;
    }


}