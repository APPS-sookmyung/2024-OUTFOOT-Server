package outfoot.outfootserver.friend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.friend.domain.Friend;

import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;

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

    public void delete(long id){
        friendRepository.deleteById(id);
    }
}