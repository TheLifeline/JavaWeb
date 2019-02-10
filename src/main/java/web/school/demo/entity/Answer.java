package web.school.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer AnswerId;

    @Lob
    @Column(nullable = false)
    private String AnswerContents;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="ProblemId")
    private Problem problem;

    @OneToMany(mappedBy = "answer",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Comment> commentList;

    public Integer getAnswerId() {
        return AnswerId;
    }

    public void setAnswerId(Integer answerId) {
        AnswerId = answerId;
    }

    public String getAnswerContents() {
        return AnswerContents;
    }

    public void setAnswerContents(String answerContents) {
        AnswerContents = answerContents;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
