package web.school.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "BSTopic")
public class BSTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer LikeNums;

    private Integer TopicReplyCount;

    private Integer TopicClickCount;

    @Column(nullable = false)
    private Integer TopicState;

    public Integer getTopicState() {
        return TopicState;
    }

    public void setTopicState(Integer topicState) {
        TopicState = topicState;
    }

    @Column(nullable = false)
    private String Topic;

    @Lob
    @Column(nullable = false)
    private String TopicContents;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private Timestamp TopicTime;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="userId")
    private User user;
    
    @OneToMany(mappedBy = "topic",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Comment> commentList;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getLikeNums() {
        return LikeNums;
    }

    public void setLikeNums(Integer likeNums) {
        LikeNums = likeNums;
    }

    public Integer getTopicReplyCount() {
        return TopicReplyCount;
    }

    public void setTopicReplyCount(Integer topicReplyCount) {
        TopicReplyCount = topicReplyCount;
    }

    public Integer getTopicClickCount() {
        return TopicClickCount;
    }

    public void setTopicClickCount(Integer topicClickCount) {
        TopicClickCount = topicClickCount;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getTopicContents() {
        return TopicContents;
    }

    public void setTopicContents(String topicContents) {
        TopicContents = topicContents;
    }

    public Timestamp getTopicTime() {
        return TopicTime;
    }

    public void setTopicTime(Timestamp topicTime) {
        TopicTime = topicTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
