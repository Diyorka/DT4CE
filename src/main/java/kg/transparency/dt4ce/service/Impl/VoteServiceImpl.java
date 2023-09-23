package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.enums.VoteType;
import kg.transparency.dt4ce.exception.NotFoundException;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.model.Vote;
import kg.transparency.dt4ce.repository.InitiativeRepository;
import kg.transparency.dt4ce.repository.VoteRepository;
import kg.transparency.dt4ce.service.VoteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    InitiativeRepository initiativeRepository;
    VoteRepository voteRepository;

    @Override
    public ResponseEntity<String> addVote(Long initiativeId, String voteType, User user) {
        Initiative initiative = initiativeRepository.findById(initiativeId)
                .filter(i -> i.getStatus() == Status.ACTIVATED)
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        if(!voteRepository.existsByInitiativeAndUser(initiative, user)){
            Vote vote = Vote.builder()
                    .voteType(VoteType.of(voteType))
                    .initiative(initiative)
                    .user(user)
                    .build();
            voteRepository.save(vote);

            if (vote.getVoteType() == VoteType.FOR) {
                initiative.setForVotesCount(initiative.getForVotesCount() + 1);
            } else {
                initiative.setAgainstVotesCount(initiative.getAgainstVotesCount() + 1);
            }
            initiativeRepository.save(initiative);
            return ResponseEntity.ok("Голос успешно добавлен");
        }

        Vote vote = voteRepository.findByInitiativeAndUser(initiative, user);

        if(vote.getVoteType() == VoteType.of(voteType)){
            if (vote.getVoteType() == VoteType.FOR) {
                initiative.setForVotesCount(initiative.getForVotesCount() - 1);
            } else {
                initiative.setAgainstVotesCount(initiative.getAgainstVotesCount() - 1);
            }

            voteRepository.delete(vote);
            return ResponseEntity.ok("Голос удален");
        }

        vote.setVoteType(VoteType.of(voteType));

        if(vote.getVoteType() == VoteType.FOR){
            initiative.setForVotesCount(initiative.getForVotesCount() + 1);
            initiative.setAgainstVotesCount(initiative.getAgainstVotesCount() - 1);
        }else{
            initiative.setForVotesCount(initiative.getForVotesCount() - 1);
            initiative.setAgainstVotesCount(initiative.getAgainstVotesCount() + 1);
        }

        voteRepository.save(vote);

        return ResponseEntity.ok("Голос успешно изменён");
    }
}
