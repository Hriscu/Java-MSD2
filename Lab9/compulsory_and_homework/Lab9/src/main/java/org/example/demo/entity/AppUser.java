package org.example.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    public AppUser() {}

    public AppUser(String username, String password, Enum<?> role) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.add(role.name());
    }

    public Long getId() { return id; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setUsername(String username) { this.username = username; }
    @Override
    public String getUsername() { return username; }

    public void setPassword(String password) { this.password = password; }
    @Override
    public String getPassword() { return password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (String r : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + r));
        }
        return list;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
