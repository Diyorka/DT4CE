package kg.transparency.dt4ce.model;

import jakarta.persistence.*;
import kg.transparency.dt4ce.enums.VoteType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vote extends BaseEntity {
    @Enumerated(EnumType.STRING)
    VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "initiative_id")
    Initiative initiative;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
