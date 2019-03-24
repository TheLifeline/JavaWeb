package web.school.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.school.demo.entity.BSTopic;
import web.school.demo.repository.TopicRepository;

import java.util.*;

@RestController
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
        return new ResponseEntity<List>(result, HttpStatus.OK) ;
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
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }
}
