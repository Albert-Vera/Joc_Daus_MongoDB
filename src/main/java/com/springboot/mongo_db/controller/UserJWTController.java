package com.springboot.mongo_db.controller;


import com.springboot.mongo_db.model.UserAdmin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserJWTController {

    @PostMapping("user")
    public UserAdmin login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        if (username.equals("admin") && pwd.equals("1234")) { // Cutre... pero solo entra el que sepa la clave
            String token = getJWTToken(username);
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setUser(username);
            userAdmin.setToken(token);
            return userAdmin;
        }else return null;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
