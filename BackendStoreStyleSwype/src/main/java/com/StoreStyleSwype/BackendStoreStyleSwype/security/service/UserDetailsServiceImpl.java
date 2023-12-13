package com.StoreStyleSwype.BackendStoreStyleSwype.security.service;

import com.StoreStyleSwype.BackendStoreStyleSwype.models.User;
import com.StoreStyleSwype.BackendStoreStyleSwype.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

// Define la clase UserDetailsServiceImpl que implementa la interfaz UserDetailsService
// de Spring Security.
public class UserDetailsServiceImpl implements UserDetailsService {

    // Inyección de la dependencia de UserRepository.
    // Este repositorio se utiliza para buscar usuarios en la base de datos.
    private UserRepository userRepository;

    // Sobreescribe el método loadUserByUsername de la interfaz UserDetailsService.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca un usuario por su nombre de usuario usando UserRepository.
        // Si el usuario no se encuentra, se lanza una excepción UsernameNotFoundException.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Convierte los roles del usuario (obtenidos de la base de datos) en GrantedAuthority
        // objetos que son utilizados por Spring Security para la autorización.
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        // Devuelve una instancia de UserDetails que contiene la información del usuario,
        // incluyendo su nombre de usuario, contraseña y autoridades


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
