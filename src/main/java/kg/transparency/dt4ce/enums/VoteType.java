package kg.transparency.dt4ce.enums;

import kg.transparency.dt4ce.exception.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum VoteType {
    FOR("За"), AGAINST("Против");

    private final String voteType;

    public static VoteType of(String voteType) {
        return Stream.of(VoteType.values())
                .filter(v -> v.getVoteType().equals(voteType))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Тип голоса не найден"));
    }
}
