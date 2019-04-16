package web.school.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.entity.User;
import web.school.demo.repository.UserRepository;
import web.school.demo.security.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;


@RestController
public class UserControl {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    @CrossOrigin
    public ResponseEntity<?> getUser(@RequestParam("id") Integer id){
        User userInfo = userRepository.findById(id).get();
        return new ResponseEntity<>(BaseResultFactory.build(userInfo),HttpStatus.OK);
    }

    @PostMapping("/user")
    @CrossOrigin
    public ResponseEntity<?> postUser(@RequestBody String jsonParam){
        LogFactory.getLog("test").info(jsonParam);
        try {
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            Integer id=Integer.valueOf(user_data.get("id"));
            User user= userRepository.findById(id).get();
            user.setName(user_data.get("userName"));
            user.setNumber(user_data.get("number"));
            user.setSchool(user_data.get("school"));
            user.setMajor(user_data.get("major"));
            user.setUserStatement(user_data.get("userStatement"));
            userRepository.save(user);
            return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/password")
    @CrossOrigin
    public ResponseEntity<?> postPossword(@RequestBody String jsonParam){
        try{
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            User user= userRepository.findById(Integer.valueOf(user_data.get("id"))).get();
            String oldPassword=user_data.get("oldPassword");
            String newPassword=user_data.get("newPassword");
            if (user.getPassword().equals(oldPassword)){
                user.setPassword(newPassword);
                userRepository.save(user);
                return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
            }else{
                throw new IOException("输入错误");
            }
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/islogin")
    @CrossOrigin
    public ResponseEntity<?> isLogin(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        HashMap<String,String> result=new HashMap<>();
        result.put("id",JwtUtil.getIDbyToken(token));
        result.put("status",JwtUtil.getRolebyToken(token));
        return new ResponseEntity<>(BaseResultFactory.build(result),HttpStatus.OK);
    }

    @PostMapping("/user/login")
    @CrossOrigin
    public ResponseEntity<?> userloggin(@RequestBody String jsonParam){
        HashMap<String,String> result=new HashMap<>();
        try{
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            String username=user_data.get("username");
            String password=user_data.get("password");
            User user=userRepository.findUserByUserId(username,password);
            if (user!=null){
                String token = JwtUtil.getToken(user.getId(),user.getStatus());
                result.put("token",token);
                result.put("id",user.getId().toString());
                result.put("status",user.getStatus());
            }
            else {
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"用户未注册或用户名或密码错误！"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }
}
