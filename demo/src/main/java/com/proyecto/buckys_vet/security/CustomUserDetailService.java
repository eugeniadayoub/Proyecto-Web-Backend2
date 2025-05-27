package com.proyecto.buckys_vet.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyecto.buckys_vet.repositorio.UserRepository;
import com.proyecto.buckys_vet.repositorio.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.proyecto.buckys_vet.entidad.UserEntity;
import java.util.Collection;
import com.proyecto.buckys_vet.entidad.Dueno;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.proyecto.buckys_vet.entidad.Role;
import java.util.Set;
import com.proyecto.buckys_vet.entidad.Veterinario;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userDB = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        UserDetails userDetails = User.withUsername(userDB.getUsername())
                .password(userDB.getPassword())
                .authorities(mapRolesToAuthorities(userDB.getRoles()))
                .accountExpired(!userDB.isAccountNonExpired())
                .accountLocked(!userDB.isAccountNonLocked())
                .credentialsExpired(!userDB.isCredentialsNonExpired())
                .disabled(!userDB.isEnabled())
                .build();

        return userDetails;

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    public UserEntity DuenoToUser(Dueno dueno) {
        UserEntity user = new UserEntity();
        // Usar prefijo "dueno_" para evitar conflictos con veterinarios
        user.setUsername("dueno_" + dueno.getNombre().toLowerCase().replace(" ", "_"));
        user.setPassword(passwordEncoder.encode(dueno.getPassword()));
        user.setEmail(dueno.getEmail());
        user.setNombre(dueno.getNombre());
        user.setCedula(dueno.getCedula());
        user.setTelefono(dueno.getTelefono());
        user.setImagenUrl(dueno.getImagenUrl());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        Role roles = roleRepository.findByName("DUENO")
                .orElseThrow(() -> new RuntimeException("Rol DUENO no encontrado"));
        user.setRoles(Set.of(roles));
        return user;
    }

    public UserEntity VetToUser(Veterinario veterinario) {
        UserEntity user = new UserEntity();
        // Usar prefijo "vet_" para evitar conflictos con dueños
        user.setUsername("vet_" + veterinario.getNombre().toLowerCase().replace(" ", "_"));
        user.setPassword(passwordEncoder.encode(veterinario.getContrasena()));
        user.setNombre(veterinario.getNombre());
        user.setCedula(veterinario.getCedula());
        user.setEspecialidad(veterinario.getEspecialidad());
        user.setNumeroAtenciones(veterinario.getNumeroAtenciones());
        // Los veterinarios no tienen email en el modelo actual, usar username como
        // email temporal
        user.setEmail(veterinario.getNombre().toLowerCase().replace(" ", ".") + "@veterinaria.com");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        Role roles = roleRepository.findByName("VETERINARIO")
                .orElseThrow(() -> new RuntimeException("Rol VETERINARIO no encontrado"));
        user.setRoles(Set.of(roles));
        return user;
    }

}
