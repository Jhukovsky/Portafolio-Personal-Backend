package com.proyecto.muestra.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Value("${jwt.secret.key}")
    private String keySecret;
    @Value("${jwt.time.expiration}")
    private String expirationTime;

    public String getToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationTime)))
                .signWith(getSignature(), SignatureAlgorithm.HS256).compact();
    }

    public Key getSignature(){
        byte[] claveBytes = Decoders.BASE64.decode(keySecret);
        return Keys.hmacShaKeyFor(claveBytes);
    }

    public boolean tokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignature())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        }catch (Exception e){
            System.out.println("Token invalido, Error: ".concat(e.getMessage()));
            return false;
        }
    }

    public Claims translateTokenClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignature())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaims(String token, Function<Claims,T> claimsTFunction){
        Claims claims = translateTokenClaims(token);
        return claimsTFunction.apply(claims);
    }

    //Obtener nombreUsuario del claim
    public String obtenerNombreUsuarioToken(String token){
        return getClaims(token, Claims::getSubject);
    }

}
