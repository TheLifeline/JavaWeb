package web.school.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.entity.BSTopic;
import web.school.demo.entity.Comment;
import web.school.demo.repository.CommentRepository;
import web.school.demo.repository.TopicRepository;
import web.school.demo.repository.UserRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
@CrossOrigin
public class TopicController {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @CrossOrigin
    @GetMapping("/topic")
    public ResponseEntity<?> getAllTopic(){
        List<Map<String, String>> result =new ArrayList<>();
        List<BSTopic> mid= topicRepository.findAll();
        for (BSTopic bsTopic : mid) {
            if(bsTopic.getTopicState().equals(1)){
                Map<String, String> midMap = new HashMap<>();
                midMap.put("id", bsTopic.getId().toString());
                midMap.put("topic", bsTopic.getTopic());
                midMap.put("likeNums",bsTopic.getLikeNums().toString());
                midMap.put("topicTime",bsTopic.getTopicTime().toString());
                midMap.put("createUser",bsTopic.getUser().getNickName());
                midMap.put("topicReplyCount",bsTopic.getTopicReplyCount().toString());
                result.add(midMap);
            }
        }
        return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
    }

    @CrossOrigin
    @GetMapping("/detail")
    public ResponseEntity<?> getDetail(@RequestParam("id") Integer id){
        BSTopic bsTopic =topicRepository.findById(id).get();
        if (bsTopic.getTopicState().equals(1)){
            Map<String, Object> result = new HashMap<>();
            result.put("topic", bsTopic.getTopic());
            result.put("likeNums",bsTopic.getLikeNums().toString());
            result.put("topicContents",bsTopic.getTopicContents());
            result.put("topicTime",bsTopic.getTopicTime().toString());
            result.put("createUser",bsTopic.getUser().getNickName());
            result.put("comments_list",bsTopic.getCommentList());
            return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
        }else{
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"请求有误！"),HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody String jsonParam){
        try{
            HashMap<String,String> data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            Comment comment = new Comment();
            comment.setCommentContents(data.get("input"));
            comment.setUser(userRepository.findById(Integer.valueOf(data.get("user_data"))).get());
            comment.setTopic(topicRepository.findById(Integer.valueOf(data.get("id"))).get());
            comment.setLikeNums(0);
            comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
            commentRepository.save(comment);
            BSTopic bsTopic=topicRepository.findById(Integer.valueOf(data.get("id"))).get();
            Integer count=bsTopic.getTopicReplyCount();
            bsTopic.setTopicReplyCount(count+1);
            topicRepository.saveAndFlush(bsTopic);
            return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping("/forum")
    public ResponseEntity<?> postForum(@RequestBody String jsonParam){
        try{
            HashMap<String,String> data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            BSTopic bsTopic =new BSTopic();
            bsTopic.setUser(userRepository.findById(Integer.valueOf(data.get("user_data"))).get());
            bsTopic.setTopic(data.get("title"));
            bsTopic.setTopicContents(data.get("value"));
            bsTopic.setLikeNums(0);
            bsTopic.setTopicClickCount(0);
            bsTopic.setTopicReplyCount(0);
            bsTopic.setTopicState(0);
            bsTopic.setTopicTime(new Timestamp(System.currentTimeMillis()));
            topicRepository.save(bsTopic);
            return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }
}
