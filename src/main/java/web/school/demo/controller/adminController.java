package web.school.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.entity.BSTopic;
import web.school.demo.entity.Comment;
import web.school.demo.entity.User;
import web.school.demo.repository.CommentRepository;
import web.school.demo.repository.TopicRepository;
import web.school.demo.repository.UserRepository;
import web.school.demo.security.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class adminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CommentRepository commentRepository;


    @PostMapping("/admin/topic")
    @CrossOrigin
    public ResponseEntity<?> getUserTopic(HttpServletRequest request, @RequestBody String jsonParam){
        String token = request.getHeader("Authorization");
        if("admin".equals(JwtUtil.getRolebyToken(token))){
            try{
                HashMap<String,String> data = new ObjectMapper().readValue(jsonParam,HashMap.class);
                Integer id=Integer.valueOf(data.get("id"));
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
                    midMap.put("likeNums",bsTopic.getLikeNums().toString());
                    midMap.put("topicTime",bsTopic.getTopicTime().toString());
                    midMap.put("createUser",bsTopic.getUser().getNickName());
                    midMap.put("topicReplyCount",bsTopic.getTopicReplyCount().toString());
                    midMap.put("state",bsTopic.getTopicState().toString());
                    result.add(midMap);
                }
                return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
            }catch (IOException e){
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"非管理员用户"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/blogAuditing")
    @CrossOrigin
    public ResponseEntity<?> postBlogAuditing(HttpServletRequest request, @RequestBody String jsonParam){
        String token = request.getHeader("Authorization");
        if("admin".equals(JwtUtil.getRolebyToken(token))){
            try{
                HashMap<String,String> data = new ObjectMapper().readValue(jsonParam,HashMap.class);
                BSTopic topic = topicRepository.findById(Integer.valueOf(data.get("id"))).get();
                topic.setTopicState(Integer.valueOf(data.get("state")));
                topicRepository.save(topic);
                return new ResponseEntity<>(BaseResultFactory.build(true), HttpStatus.OK) ;
            }catch (IOException e){
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"非管理员用户"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/deleteComment")
    @CrossOrigin
    public ResponseEntity<?> postDeleteComment(HttpServletRequest request, @RequestBody String jsonParam){
        String token = request.getHeader("Authorization");
        if("admin".equals(JwtUtil.getRolebyToken(token))){
            try{
                HashMap<String,String> data = new ObjectMapper().readValue(jsonParam,HashMap.class);
                commentRepository.deleteCommentById(Integer.valueOf(data.get("id")));
                return new ResponseEntity<>(BaseResultFactory.build(true), HttpStatus.OK) ;
            }catch (IOException e){
                return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"非管理员用户"), HttpStatus.NOT_FOUND);
        }
    }
    
    @CrossOrigin
    @GetMapping("/admin/detail")
    public ResponseEntity<?> getDetail(HttpServletRequest request, @RequestParam("id") Integer id){
    	String token = request.getHeader("Authorization");
        if("admin".equals(JwtUtil.getRolebyToken(token))){
            BSTopic bsTopic =topicRepository.findById(id).get();
            Map<String, Object> result = new HashMap<>();
            result.put("topic", bsTopic.getTopic());
            result.put("likeNums",bsTopic.getLikeNums().toString());
            result.put("topicContents",bsTopic.getTopicContents());
            result.put("topicTime",bsTopic.getTopicTime().getTime());
            result.put("createUser",bsTopic.getUser().getNickName());
            List<Map<String,Object>> CommentList= new ArrayList<Map<String,Object>>();
            List<Comment> l = bsTopic.getCommentList();
            for(int i=0; i<l.size();i++) {
            	Comment comment=l.get(i);
            	Map<String,Object> mid= new HashMap<String,Object>();
            	mid.put("id", comment.getId());
            	User u = comment.getUser();
            	mid.put("userName", u.getNickName());
            	mid.put("userID", u.getId());
            	mid.put("likeNum",comment.getLikeNums());
            	mid.put("commentTime", comment.getCommentTime().getTime());
            	mid.put("commentContent", comment.getCommentContents());
            	CommentList.add(mid);
            }
            result.put("comments_list",CommentList);
            return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"非管理员用户"), HttpStatus.NOT_FOUND);
        }

    }
    
    @CrossOrigin
    @GetMapping("/admin/getAllUser")
    public ResponseEntity<?> getAllUser(HttpServletRequest request){
    	String token = request.getHeader("Authorization");
        if("admin".equals(JwtUtil.getRolebyToken(token))){
        	List<User> result = userRepository.findUserByStatus("user");
            return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.NOT_FOUND.value(),"非管理员用户"), HttpStatus.NOT_FOUND);
        }

    }
}
