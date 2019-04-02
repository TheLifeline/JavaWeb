package web.school.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.entity.BSTopic;
import web.school.demo.repository.TopicRepository;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
public class TopicController {
    @Autowired
    TopicRepository topicRepository;

    @CrossOrigin
    @GetMapping("/topic")
    public ResponseEntity<?> getAllTopic(){
        List<Map<String, String>> result =new ArrayList<>();
        List<BSTopic> mid= topicRepository.findAll();
        for (BSTopic bsTopic : mid) {
            Map<String, String> midMap = new HashMap<>();
            midMap.put("id", bsTopic.getId().toString());
            midMap.put("topic", bsTopic.getTopic());
            midMap.put("likeNums",bsTopic.getLikeNums().toString());
            midMap.put("topicTime",bsTopic.getTopicTime().toString());
            midMap.put("createUser",bsTopic.getUser().getUserName());
            midMap.put("topicReplyCount",bsTopic.getTopicReplyCount().toString());
            result.add(midMap);
        }
        return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
    }

    @CrossOrigin
    @GetMapping("/detail")
    public ResponseEntity<?> getDetail(@RequestParam("id") Integer id){
        BSTopic bsTopic =topicRepository.findById(id).get();
        Map<String, String> result = new HashMap<>();
        result.put("topic", bsTopic.getTopic());
        result.put("likeNums",bsTopic.getLikeNums().toString());
        result.put("topicContents",bsTopic.getTopicContents());
        result.put("topicTime",bsTopic.getTopicTime().toString());
        result.put("createUser",bsTopic.getUser().getUserName());
        return new ResponseEntity<>(BaseResultFactory.build(result), HttpStatus.OK) ;
    }

    @CrossOrigin
    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody String jsonParam){
        try{
            HashMap<String,String> user_data = new ObjectMapper().readValue(jsonParam,HashMap.class);
            String input=user_data.get("input");
            String id=user_data.get("id");
            LoggerFactory.getLogger("MY LOG!").info(input);
            return new ResponseEntity<>(BaseResultFactory.build(true),HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(BaseResultFactory.build(HttpStatus.BAD_REQUEST.value(),"输入错误"),HttpStatus.BAD_REQUEST);
        }
    }
}
