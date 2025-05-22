package com.travel.common.utilis;

/**
 * @package: com.jyx.happytravel.untils.JwtUtil
 * @author: 景亚雄
 * @description: TODO:jwt 工具类
 * @date: 2024/3/24 18:33
 */


import com.alibaba.druid.util.StringUtils;
import com.travel.common.exce.GlobalException;
import com.travel.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtUtil {

    private String tokenSignKey;
    private Long tokenExpiration;

    //生成token字符串
    public  String createToken(Long userId) {
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration*1000*60)) //单位分钟
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public  Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Long.valueOf(claims.get("userId").toString());
    }



    //判断token是否有效
    public  boolean isExpiration(String token){
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
            //没有过期，有效，返回false
            return isExpire;
        }catch(Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }

    //致token失效
    public void tokenExpiration(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            claims.remove("userId", getUserId(token));

        }catch (Exception e){
            throw new GlobalException(ResultCodeEnum.SERVICE_ERROR);
        }
    }
}
