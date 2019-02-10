package web.school.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Lob
    @Column(nullable = false)
    private String ProblemContents;

    @OneToMany(mappedBy = "problem",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Answer> answerList;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getProblemContents() {
        return ProblemContents;
    }

    public void setProblemContents(String problemContents) {
        ProblemContents = problemContents;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
