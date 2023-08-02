package com.app.learningcards.models;

import com.app.learningcards.models.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Users")
@Table(name = "Users",
        uniqueConstraints = {
            @UniqueConstraint(name = "user_login_unique", columnNames = "login" ),
            @UniqueConstraint(name = "user_email_unique", columnNames = "email" )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails
{
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName="user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "login",
            nullable = false
    )
    private String login;
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    public List<Recipe> favRecipes;
    public User(String login, String password, String email, Role role)
    {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.favRecipes = new LinkedList<>();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername()
    {
        return login;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
