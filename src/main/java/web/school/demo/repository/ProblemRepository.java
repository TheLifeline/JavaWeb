package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.school.demo.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem,Integer> {
}
