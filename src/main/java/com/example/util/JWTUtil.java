package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exp.UnAuthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    private static final String secretKey = "!maz234^gikey";
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-hour
    private static final int emailTokenLiveTime = tokenLiveTime * 24; // 1-day
    public static String encode(String email, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role.toString());

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            String email = (String) claims.get("email");

            String role = (String) claims.get("role");
            ProfileRole profileRole = ProfileRole.valueOf(role);

            return new JwtDTO(email, profileRole);
        }catch (JwtException e){
            throw new UnAuthorizedException("your session is expired");
        }
    }

    public static String encodeEmailJwt(Integer id) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", id);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.setIssuer("kunuz test portali");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeEmailJwt(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            Integer id = (Integer) claims.get("id");
            return new JwtDTO(id, null);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }
}
