package com.tsingtec.mini.config.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tsingtec.mini.constants.Constants;
import com.tsingtec.mini.entity.mini.MaUser;
import org.springframework.beans.factory.annotation.Autowired;
import com.auth0.jwt.JWT;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service("JwtUtil")
@CacheConfig(cacheNames = {"token"})
public class JwtUtil {
 
    @Autowired
    private JwtProperties jwtProperties;
 


    @Cacheable(key="#maUser.id")
    public String getToken(MaUser maUser) {
        System.out.println("没有缓存!");
        Date date = new Date(System.currentTimeMillis() + jwtProperties.getTokenExpireTime()*60*1000l);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        String token = JWT.create().withHeader(header).withClaim("openid",maUser.getOpenId())
                .withClaim("id",maUser.getId()).withExpiresAt(date).sign(algorithm);
        return token;
    }

    public boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}