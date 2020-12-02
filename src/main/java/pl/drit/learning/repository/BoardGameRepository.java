package pl.drit.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.drit.learning.entity.BoardGameDetails;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGameDetails, Long> {

}
