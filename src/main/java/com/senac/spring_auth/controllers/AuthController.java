package com.senac.spring_auth.controllers;

import com.senac.spring_auth.dtos.LoginRequest;
import com.senac.spring_auth.dtos.LoginResponse;
import com.senac.spring_auth.entities.Usuario;
import com.senac.spring_auth.repositories.UsuarioRepository;
import com.senac.spring_auth.services.JWTService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, UserDetailsService userDetailsService, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return this.usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.email);

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
