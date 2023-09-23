package kg.transparency.dt4ce.model;

import jakarta.persistence.*;
import kg.transparency.dt4ce.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "initiative")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Initiative extends BaseEntity{
    String title;

    @Column(length = 1000)
    String description;

    int viewsCount;

    int forVotesCount;

    int againstVotesCount;

    @Enumerated(EnumType.STRING)
    Status status;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    List<String> imageUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
