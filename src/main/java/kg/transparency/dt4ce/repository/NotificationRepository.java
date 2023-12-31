package kg.transparency.dt4ce.repository;

import kg.transparency.dt4ce.model.Notification;
import kg.transparency.dt4ce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
}
