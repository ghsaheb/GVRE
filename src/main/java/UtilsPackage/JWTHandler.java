package UtilsPackage;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;

public class JWTHandler {
    //Sample method to construct a JWT
    public static String createJWT(String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Let's set the JWT Claims
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder()
                    .setIssuedAt(now)
                    .setSubject(subject)
                    .setIssuer("GVRE")
                    .signWith(signatureAlgorithm, "mozi-amoo".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("jwt:"+builder.compact());

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static String parseJWT(String jwt) throws UnsupportedEncodingException {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey("mozi-amoo".getBytes("UTF-8"))
                .parseClaimsJws(jwt).getBody();
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        return claims.getSubject();
    }


}