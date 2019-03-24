package web.school.demo.controller;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.entity.User;
import web.school.demo.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserControl {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    @CrossOrigin
    public User getUser(@PathVariable("id") Integer id){
        User data = userRepository.findById(id).get();
        return data;
    }

//    @PostMapping("/user/login")
//    @CrossOrigin
//    public ResponseEntity<?> userloggin(HttpServletRequest request, String username, String password){
//        LogFactory.getLog("MY LOG!").info(username);
////        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
