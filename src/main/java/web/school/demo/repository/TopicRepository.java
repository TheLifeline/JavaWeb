package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.school.demo.entity.BSTopic;

public interface TopicRepository extends JpaRepository<BSTopic,Integer> {
}
