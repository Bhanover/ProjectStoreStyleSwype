package com.StoreStyleSwype.BackendStoreStyleSwype.security.service;

import com.StoreStyleSwype.BackendStoreStyleSwype.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
// La clase UserDetailsImpl es una implementación personalizada de la interfaz UserDetails de Spring Security.
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    // Propiedades básicas del usuario
    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor de la clase, inicializa los atributos del usuario.
    public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Método que devuelve las autoridades (roles y permisos) del usuario.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Método para obtener el ID del usuario.
    public Long getId() {
        return id;
    }

    // Método para obtener el email del usuario.
    public String getEmail() {
        return email;
    }

    // Método para obtener la contraseña del usuario.
    @Override
    public String getPassword() {
        return password;
    }

    // Método para obtener el nombre de usuario.
    @Override
    public String getUsername() {
        return username;
    }

    // Métodos que indican el estado de la cuenta del usuario.
    // Actualmente, todos devuelven true, lo que indica que la cuenta no está expirada, bloqueada,
    // y que las credenciales no están expiradas. También indica que el usuario está habilitado.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Método estático para construir un UserDetailsImpl a partir de un objeto User.
    // Convierte los roles del usuario a una lista de GrantedAuthority.
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        System.out.println("User roles: " + authorities);

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}