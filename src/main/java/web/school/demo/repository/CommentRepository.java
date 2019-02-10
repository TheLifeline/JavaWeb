package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.school.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
