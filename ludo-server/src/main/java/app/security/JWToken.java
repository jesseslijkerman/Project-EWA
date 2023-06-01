package app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class JWToken {
    private static final JWTokenInfo jwtInfo = new JWTokenInfo();

    @Value("${jwt.issuer:MyOrganisation}")
    private String issuer;

    private final String passphrase = "Mens erger je niet, je kunt er eigenlijk niet omheen draaien, het is een klotespel";
    private final int expiration = 7200;

    public String createToken(long userId, String userName, String countryCode, String password, String email) {
        Key signKey = generateKey(passphrase);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("userName", userName)
                .claim("countryCode", countryCode)
                .claim("password", password)
                .claim("email", email)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(signKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private Key generateKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    private JWTokenInfo fillJWTInfo(Claims claims) {
        jwtInfo.setId(Long.parseLong(claims.get("userId").toString()));
        jwtInfo.setFirstName(claims.get("userName").toString());
        jwtInfo.setLastName(claims.get("countryCode").toString());
        jwtInfo.setPassword(claims.get("password").toString());
        jwtInfo.setEmail(claims.get("email").toString());
        jwtInfo.setIssuedAtDate(new Date());
        jwtInfo.setExpirationDate(new Date(System.currentTimeMillis() + expiration * 1000L));

        return jwtInfo;
    }

    public JWTokenInfo decodeToken(String encodedToken, boolean expirationLenient) throws AuthenticationException {
        try {
            Key key = generateKey(passphrase);
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(passphrase.getBytes())
                    .build()
                    .parseClaimsJws(encodedToken);
            Claims claims = jws.getBody();
            return fillJWTInfo(claims);

        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (ExpiredJwtException e) {
            if (!expirationLenient) {
                throw new AuthenticationException(e.getMessage());
            } else {
                return fillJWTInfo(e.getClaims());
            }
        }
    }

    public JWTokenInfo authenticateToken(String token) {
        if (token != null && !token.isEmpty() && !token.equalsIgnoreCase("null")) {
            try {
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(passphrase.getBytes())
                        .build()
                        .parseClaimsJws(token);

                Claims claims = jws.getBody();
                return fillJWTInfo(claims);
            } catch (SignatureException | IllegalArgumentException | MalformedJwtException | UnsupportedJwtException ignored) {
            } catch (ExpiredJwtException e) {
                if (isTokenRenewable()) {
                    return fillJWTInfo(e.getClaims());
                }
            }
        }
        return null;
    }

    public Map<String, String> assembleTokenData() {
        Map<String, String> tokenData = new LinkedHashMap<>();
        tokenData.put("userId", String.valueOf(jwtInfo.getId()));
        tokenData.put("firstName", jwtInfo.getFirstName());
        tokenData.put("lastName", jwtInfo.getLastName());
        tokenData.put("email", jwtInfo.getEmail());
        tokenData.put("password", jwtInfo.getPassword());
        tokenData.put("issuedAtDate", String.valueOf(jwtInfo.getIssuedAtDate()));
        tokenData.put("expirationDate", String.valueOf(jwtInfo.getExpirationDate()));
        tokenData.put("token", createToken(jwtInfo.getId(), jwtInfo.getFirstName(), jwtInfo.getLastName(), jwtInfo.getPassword(), jwtInfo.getEmail()));
        return tokenData;
    }

    public boolean isTokenRenewable() {
        if (jwtInfo.getEmail() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jwtInfo.getIssuedAtDate());
            calendar.add(Calendar.SECOND, expiration);
            return calendar.getTime().compareTo(new Date()) > 0;
        }
        return false;
    }

    public void logOut() {
        jwtInfo.empty();
    }
}
