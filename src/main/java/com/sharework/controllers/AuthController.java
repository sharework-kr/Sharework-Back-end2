package com.sharework.controllers;

import com.sharework.models.ERole;
import com.sharework.models.Role;
import com.sharework.models.User;
import com.sharework.payload.request.LoginRequest;
import com.sharework.payload.request.SignupRequest;
import com.sharework.payload.request.SignupsRequest;
import com.sharework.payload.response.JwtResponse;
import com.sharework.payload.response.MessageResponse;
import com.sharework.repository.RoleDao;
import com.sharework.repository.UserDao;
import com.sharework.global.security.jwt.JwtUtils;
import com.sharework.global.security.services.UserDetailsImpl;
//import com.sharework.repository.UsersDao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;


    private final UserDao userDao;

//    private final UsersDao usersDao;

    private final RoleDao roleDao;


    private final PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByName(ERole.ROLE_WORKER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "giver":
                        Role adminRole = roleDao.findByName(ERole.ROLE_GIVER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleDao.findByName(ERole.ROLE_WORKER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userDao.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signups")
    public ResponseEntity<?> registerUsers(@Valid @RequestBody SignupsRequest signUpsRequest, BindingResult bindingResult) {
        System.out.println(signUpsRequest.toString());

//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: 정확한 정보 기입 필요."));
//        } else if (usersDao.existsByEmail(signUpsRequest.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: 이메일이 존재합니다."));
//        } else if (usersDao.existsByPhoneNumber(signUpsRequest.getPhoneNumber())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: 번호가 존재합니다."));
//        }

//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_WORKER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "giver":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_GIVER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        break;
//
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_WORKER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
