package com.tsingtec.mini.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("JwtUtil")
public class JwtUtil {
 
    @Autowired
    private JwtProperties jwtProperties;

    @Cacheable(value="token",key="#mpUser.unionId")
    public String getToken(MpUser mpUser) {
        log.info("公众号或小程序--没有缓存!");
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        Date exp = new Date(System.currentTimeMillis() + jwtProperties.getTokenExpireTime()*60*1000l);
        // 头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim("unionid",mpUser.getUnionId())
                .withClaim("id",mpUser.getId().toString())
                .withExpiresAt(exp)//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        return token;
    }

    @Cacheable(value="token",key="#p0+#p1")
    public String createToken(String claim,String val) {
        System.out.println("没有缓存!");
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        Date exp = new Date(System.currentTimeMillis() + jwtProperties.getTokenExpireTime()*60*1000l);
        // 头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim(claim,val)
                .withExpiresAt(exp)//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        return token;
    }

    /**
     * 登录二维码3分钟有效
     * @param sessionid
     * @return
     */
    @Cacheable(value ="login", key="#p0")
    public String loginToken(String sessionid) {
        log.info("login-token empty!");
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        Date exp = new Date(System.currentTimeMillis() + 3*60*1000l);
        // 头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim("sessionid",sessionid)
                .withExpiresAt(exp)//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        return token;
    }

    /**
     * 创建同意的缓存
     * @return
     */
    @CachePut(value ="login", key="'aggre'+#p0")
    public String createAgree(String sessionid,String loginName) {
        log.info("创建二维码扫描登录同意缓存:{sessionid--%s,loginName--%s}",sessionid,loginName);
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        Date exp = new Date(System.currentTimeMillis() + 3*60*1000l);
        // 头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)// 设置头部信息 Header
                .withClaim("sessionid",sessionid)
                .withClaim("loginName",loginName)
                .withExpiresAt(exp)//设置 载荷 签名过期的时间
                .sign(algorithm);//签名 Signature
        return token;
    }

    /**
     * 获取同意的缓存
     * @return
     */
    @Cacheable(value ="login", key="'aggre'+#p0")
    public String getAgree(String sessionid) {
        log.info("进来了说明没有缓存:{sessionid--%s}",sessionid);
        return "";
    }



    public boolean verify(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getClaim(String token, String claim) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.secretKey);
        try{
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims().get(claim).asString();
        }catch (IllegalArgumentException e) {
            throw new BusinessException(BaseExceptionType.TOKEN_ERROR,"token认证失败");
        }catch (JWTVerificationException e) {
            throw new BusinessException(BaseExceptionType.TOKEN_ERROR,"token认证失败");
        }
    }
}