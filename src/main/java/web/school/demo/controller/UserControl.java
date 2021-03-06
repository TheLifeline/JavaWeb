package web.school.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.entity.BSTopic;
import web.school.demo.entity.User;
import web.school.demo.repository.TopicRepository;
import web.school.demo.repository.UserRepository;
import web.school.demo.security.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserControl {
	@Autowired
    TopicRepository topicRepository;
	
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    @CrossOrigin
    public ResponseEntity<?> getUser(@RequestParam("id") Integer id){
        User userInfo = userRepository.findById(id).get();
        return new ResponseEntity<>(BaseResultFactory.build(userInfo),HttpStatus.OK);
    }
    
    @GetMapping("/user/info")
    @CrossOrigin
    public ResponseEntity<?> getUserDetail(HttpServletRequest request){
    	String token = request.getHeader("Authorization");
		Integer id = new Integer(JwtUtil.getIDbyToken(token));
		User user= userRepository.findById(id).get();
		return new ResponseEntity<>(BaseResultFactory.build(user), HttpStatus.OK) ;
    }

    @PostMapping("/user")
    @CrossOrigin
    public ResponseEntity<?> postUser(@RequestBody String jsonParam){
        LogFactory.getLog("test").info(jsonParam);
        try {
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            Integer id=Integer.valueOf(user_data.get("id"));
            User user= userRepository.findById(id).get();
            user.setName(user_data.get("name"));
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
            User user=userRepository.findUserByNickNameAndPassword(username,password);
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

    @PostMapping("/user/register")
    @CrossOrigin
    public ResponseEntity<?> userRegister(@RequestBody String jsonParam){
    	LogFactory.getLog("test").info("test---");
        try{
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            User user=new User();
            String username=user_data.get("username");
            String password=user_data.get("password");
            String email=user_data.get("email");
            User u = userRepository.findUserByNickName(username);
            if(u!=null){
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"用户名已使用！"),HttpStatus.BAD_REQUEST);
            }
            if(password.length()<8 || password.length()>15){
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"长度不够"),HttpStatus.BAD_REQUEST);
            }
            user.setPassword(password);
            user.setNickName(username);
            user.setEmail(email);
            user.setStatus("user");
            user.setUserRegisterDate(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            return new ResponseEntity<>(BaseResultFactory.build(true), HttpStatus.OK);
        }catch (IOException e)
        {
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/user/topic")
    @CrossOrigin
    public ResponseEntity<?> userTopic(HttpServletRequest request){
    	String token = request.getHeader("Authorization");
		Integer id = new Integer(JwtUtil.getIDbyToken(token));
		List<Map<String, String>> result = new ArrayList<>();
		List<BSTopic> mid = null;
		if(id.equals(0)){
		    mid = topicRepository.findAll();
		}else {
			User user = userRepository.findById(id).get();
			mid = user.getTopicList();
		}
		for (BSTopic bsTopic : mid) {
		    Map<String, String> midMap = new HashMap<>();
		    midMap.put("id", bsTopic.getId().toString());
		    midMap.put("topic", bsTopic.getTopic());
		    midMap.put("topicTime",bsTopic.getTopicTime().toString());
		    midMap.put("createUser",bsTopic.getUser().getNickName());
		    result.add(midMap);
		}
		return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
    }


}
