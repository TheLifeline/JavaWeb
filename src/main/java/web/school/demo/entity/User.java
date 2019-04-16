package web.school.demo.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

//使用JPA注解配置映射关系
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer Id;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String nickName;

    private String Name;

    @Column(nullable = false)
    private String Password;

    private String Email;

    private String School;

    private String major;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    @Lob
    private String UserStatement;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private Timestamp UserRegisterDate;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<BSTopic> topicList;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Comment> commentList;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserStatement() {
        return UserStatement;
    }

    public void setUserStatement(String userStatement) {
        UserStatement = userStatement;
    }

    public Timestamp getUserRegisterDate() {
        return UserRegisterDate;
    }

    public void setUserRegisterDate(Timestamp userRegisterDate) {
        UserRegisterDate = userRegisterDate;
    }

    public List<BSTopic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<BSTopic> topicList) {
        this.topicList = topicList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
