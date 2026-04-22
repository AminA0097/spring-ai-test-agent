package com.webapp.arvand.coreback.Jwt;

import com.webapp.arvand.coreback.Utils.PemUtils;
import io.jsonwebtoken.*;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    private final PemUtils pemUtils;
    private final long EXPIRATION = 1000 * 60 * 15;
    public JwtService(PemUtils pemUtils) {
        this.pemUtils = pemUtils;
    }

    public String generateToken(Long id,String jti) {

        return Jwts.builder()
                .setId(jti)
                .setSubject(String.valueOf(id))
                .setIssuer("Ai-Agent-01")
                .setAudience("Ai-Agent-01")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(pemUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(pemUtils.getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e) {
            return null;
        }
    }
    public String extractEmail(String token,String key) {
        return extractAllClaims(token).get("email", String.class);
    }
    public Long extractLongValue(String token,String key) {
        return Long.valueOf(extractAllClaims(token).get(key).toString());
    }

}
