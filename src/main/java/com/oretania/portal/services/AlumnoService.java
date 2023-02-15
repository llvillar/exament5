package com.oretania.portal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oretania.portal.models.Alumno;
import com.oretania.portal.models.Asignatura;
import com.oretania.portal.repositories.AlumnoRepository;

@Service
public class AlumnoService implements UserDetailsService {
    

    @Autowired
    AlumnoRepository repository;

    public List<Alumno> findAll(){
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Alumno alumno = repository.findByUserName(username);

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        List<Asignatura> asignaturas = alumno.getAsignaturas();

        for (Asignatura a : asignaturas) {
            roles.add(new SimpleGrantedAuthority(a.getCodigo()));
        }

        // UserDetails userDetails = new User(user.getName(), user.getPassword(),
        // roles);
        UserDetails userDetails = User.builder()
                .username(alumno.getUserName())
                .password(alumno.getPassword())
                .authorities(roles)
                .build();
        return userDetails;
    }
}
