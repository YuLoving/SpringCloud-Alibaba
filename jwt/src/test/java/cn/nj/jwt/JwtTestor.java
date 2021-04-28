package cn.nj.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;

/**
 * @author ：zty
 * @date ：Created in 2021/4/29 1:11
 * @description ：
 */
@SpringBootTest(classes = JwtApplication.class)
@RunWith(SpringRunner.class)
public class JwtTestor {

    private final static  String KEY="1234567890_1234567890_1234567890";

    /**
     * 模拟真实环境 UserID 为 123 号的用户登录后的 JWT 生成过程。
     */
    @Test
    public void createJwt(){
        //1.对密钥做base64编码
        String encode = new BASE64Encoder().encode(KEY.getBytes());
        //2.生成的密钥对象 会根据base64长度自动选择相应的HMAC算法
        SecretKey secretKey = Keys.hmacShaKeyFor(encode.getBytes());
        //3.利用JJWT生成token
        //载荷数据
        String data = "{\"userId\":123}";

        String jwt = Jwts.builder().setSubject(data).signWith(secretKey).compact();
        System.out.println(jwt);
    }


    /**
     * 验签代码，从 JWT 中提取 123 号用户数据。这里要保证 JWT 字符串、key 私钥与生成时保持一致。否则就会抛出验签失败 JwtException。
     */
    @Test
    public void checkJwt(){
        //1.createJwt()生产的jwt
        String jwt="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjoxMjN9In0.1p_VTN46sukRJTYFxUg93CmfR3nJZRBm99ZK0e3d9Hw";

        //2. 对密钥进行base64编码
        String encode = new BASE64Encoder().encode(KEY.getBytes());

        //3.生成密钥对象 会根据base64长度自动选择相应的HMAC算法
        SecretKey secretKey = Keys.hmacShaKeyFor(encode.getBytes());
        //4.认证token
        try {

            //生成JWT解析器
            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            //解析JWT
            Jws<Claims> claimsJws = parser.parseClaimsJws(jwt);
            //得到载荷中的用户数据
            String subject = claimsJws.getBody().getSubject();
            System.out.println(subject);
        }catch (JwtException e){
            //所有关于Jwt校验的异常都继承自JwtException
            System.out.println("Jwt校验失败");
            e.printStackTrace();
        }


    }

}
