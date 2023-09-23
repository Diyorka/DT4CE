package kg.transparency.dt4ce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity{
    String header;

    String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(columnDefinition = "boolean default false")
    boolean read;
}
