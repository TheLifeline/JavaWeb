package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.school.demo.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
