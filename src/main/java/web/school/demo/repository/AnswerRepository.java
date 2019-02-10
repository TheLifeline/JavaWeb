package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.school.demo.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
