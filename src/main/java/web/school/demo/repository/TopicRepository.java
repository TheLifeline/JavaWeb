package web.school.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.school.demo.entity.BSTopic;

import java.util.List;

public interface TopicRepository extends JpaRepository<BSTopic,Integer> {
    @Query(value = "select * from bstopic where topic like ?1", nativeQuery = true)
    List<BSTopic> searchdata(String searchData);
}
