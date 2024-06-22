package outfoot.outfootserver.freind.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.freind.domain.Friend;

import outfoot.outfootserver.freind.dto.AddFriendRequest;
import outfoot.outfootserver.freind.exception.AuthErrorCode;
import outfoot.outfootserver.freind.exception.AuthException;
import outfoot.outfootserver.freind.repository.FriendRepository;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional
    public long save(AddFriendRequest dto){
        friendRepository.findByNickname(dto.getNickname()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.FRIEND_DUPLICATED);
        });
        Friend friend = AddFriendRequest.toFriend(dto);
        Friend savedFriend = friendRepository.save(friend);
        return savedFriend.getId();
    }
}