package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.school.demo.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from user where nick_name=?1 and password =?2", nativeQuery = true)
    User findUserByUserId(String username,String password);
}
