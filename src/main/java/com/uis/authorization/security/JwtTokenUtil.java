package com.uis.authorization.security;

import com.uis.authorization.exception.TechnicalException;
import com.uis.authorization.model.AccessControl;
import com.uis.authorization.repository.IAccessControlRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -8376908656562126593L;
    private IAccessControlRepository accessControlRepository;

    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //get token without bearer
    public String getTokenWithoutBearer(String token) {
        if(token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new TechnicalException("Token not found");
    }

    //generate token for user
    public String generateToken(UserDetails userDetails, Long idUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUser", idUser);
        String token = doGenerateToken(claims, userDetails.getUsername());
        AccessControl lastJwt = this.accessControlRepository.findDistinctTopByIdUser(idUser).orElse(null);
        if(lastJwt != null) {
            lastJwt.setToken(BCrypt.hashpw(token, BCrypt.gensalt(10)));
            lastJwt.setLastAccess(new Date());
            this.accessControlRepository.save(lastJwt);
        } else {
            AccessControl accessControl = new AccessControl();
            accessControl.setIdUser(idUser);
            accessControl.setToken(BCrypt.hashpw(token, BCrypt.gensalt(10)));
            accessControl.setLastAccess(new Date());
            this.accessControlRepository.save(accessControl);
        }
        return token;
    }

    //creating token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().
                setClaims(claims).
                setSubject(subject).
                signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);
        Claims claims = getAllClaimsFromToken(token);
        AccessControl accessControl = accessControlRepository.findDistinctTopByIdUser(Long.parseLong(claims.get("idUser").toString()))
                .orElseThrow(() -> new TechnicalException("User not found"));
        return (username.equals(user.getUsername()) && this.passwordEncoder().matches(token, accessControl.getToken()));
    }

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setAccessControlRepository(IAccessControlRepository accessControlRepository) {
        this.accessControlRepository = accessControlRepository;
    }
}
