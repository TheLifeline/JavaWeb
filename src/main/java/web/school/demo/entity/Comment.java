package web.school.demo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private Timestamp CommentTime;

    @Lob
    private String CommentContents;

    private Integer LikeNums;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "topicId")
    private BSTopic topic;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "answerId")
    private Answer answer;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Timestamp getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        CommentTime = commentTime;
    }

    public String getCommentContents() {
        return CommentContents;
    }

    public void setCommentContents(String commentContents) {
        CommentContents = commentContents;
    }

    public Integer getLikeNums() {
        return LikeNums;
    }

    public void setLikeNums(Integer likeNums) {
        LikeNums = likeNums;
    }

    public BSTopic getTopic() {
        return topic;
    }

    public void setTopic(BSTopic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
