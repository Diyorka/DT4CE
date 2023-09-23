package kg.transparency.dt4ce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity{
    String text;

    @CreationTimestamp
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "initiative_id")
    Initiative initiative;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
