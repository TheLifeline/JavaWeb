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

import java.io.IOException;
import java.util.HashMap;


@RestController
public class UserControl {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    @CrossOrigin
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
        User data = userRepository.findById(id).get();
        return new ResponseEntity<>(BaseResultFactory.build(data),HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    @CrossOrigin
    public ResponseEntity<?> postUser(@PathVariable("id") Integer id,@RequestBody String jsonParam){
        try {
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            User user= userRepository.findById(id).get();
            user.setUserName(user_data.get("username"));
            user.setNumber(user_data.get("number"));
            user.setSchool(user_data.get("school"));
            user.setProject(user_data.get("project"));
            user.setUserStatement(user_data.get("statement"));
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
            user.setPassword(user_data.get("password"));
            userRepository.save(user);
            return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/islogin")
    @CrossOrigin
    public ResponseEntity<?> isLogin(){
        Boolean isLogin=true;
        return new ResponseEntity<>(BaseResultFactory.build(isLogin),HttpStatus.OK);
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
                String token = JwtUtil.getToken(user.getUserName());
                result.put("token",token);
                result.put("id",user.getId().toString());
            }
            else {
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"用户未注册！"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }
}
