package outfoot.outfootserver.freind.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.freind.domain.Friend;

import outfoot.outfootserver.freind.exception.AuthErrorCode;
import outfoot.outfootserver.freind.exception.AuthException;
import outfoot.outfootserver.freind.repository.FriendRepository;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional
    public long save(Friend friend) {
        friendRepository.findByNickname(friend.getNickname()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.FRIEND_DUPLICATED);
        });

        Friend savedFriend = friendRepository.save(friend);
        return savedFriend.getId(); //저장 엔티티 ID 반환

    }
}