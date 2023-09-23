package kg.transparency.dt4ce.repository;

import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
    Page<Initiative> findAllByStatus(Status status, Pageable pageable);

    Page<Initiative> findAllByUserAndStatusIsNot(User user, Status status, Pageable pageable);
}
