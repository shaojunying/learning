package com.shao.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.shao.Domain.User;
import com.shao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * Created by shao on 2019/4/12 13:42.
 */
@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    public String getToken(User user){
        String token = "";
        try {
            token = JWT.create()
                    .withAudience(String.valueOf(user.getId()))
                    .sign(Algorithm.HMAC256(user.getPassword()));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public Optional<User> parseToken(String token){
        Optional<User> user;
        try {
            List<String> result = JWT.decode(token).getAudience();
            if (result==null || result.size() == 0 ){
                return Optional.empty();
            }
            user = userRepository.findById(Long.valueOf(result.get(0)));
            return user;
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
