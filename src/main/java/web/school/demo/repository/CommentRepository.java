package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import web.school.demo.entity.Comment;
import web.school.demo.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from comment when id=?1", nativeQuery = true)
    void  deleteCommentById(Integer id);
}
