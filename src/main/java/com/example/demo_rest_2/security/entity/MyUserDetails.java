package com.example.demo_rest_2.security.entity;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails, CredentialsContainer, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String email;
    private String password; // локальная копия, очищаемая eraseCredentials()
    private final boolean enabled;
    private final List<GrantedAuthority> authorities;
    private final String firstName;
    private final String lastName;

    public MyUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword(); // копируем
        this.enabled = user.isEnabled();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authorities = user.getRoles().stream()
                .map(Role::toAuthority)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }

    @Override
    public void eraseCredentials() { this.password = null;}  // не трогаем entity

}
