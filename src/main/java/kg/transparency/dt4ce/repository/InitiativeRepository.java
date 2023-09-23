package kg.transparency.dt4ce.repository;

import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
    Page<Initiative> findAllByStatus(Status status, Pageable pageable);

    Page<Initiative> findAllByUserAndStatusIsNot(User user, Status status, Pageable pageable);

    @Query("SELECT MAX(i.createdAt) FROM Initiative i WHERE i.user = :user")
    LocalDateTime findLastCreationTimeByUser(@Param("user") User user);
}
