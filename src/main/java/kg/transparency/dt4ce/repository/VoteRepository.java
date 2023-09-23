package kg.transparency.dt4ce.repository;

import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByInitiativeAndUser(Initiative initiative, User user);

    Vote findByInitiativeAndUser(Initiative initiative, User user);
}
