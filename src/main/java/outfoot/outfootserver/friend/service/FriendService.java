package outfoot.outfootserver.friend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addFriend(AddFriendRequest dto, Long memberId, Long friendMemberId){
//        friend를 빌드할 때 친구의 멤버 아이디를 넣어야지! 했는데 아이디 못 넣음 이슈 어떡함?
//        Friend friend = Friend.builder()
//                .memberId(memberId)
//                .friendMemberId(friendMemberId())
//                .nickname(dto.getNickname())
//                .build(); --> 하다가 불가능을 깨달은 원래 계획

        Friend friend = dto.toFriend();
        friend = friendRepository.save(friend);
        return friend.getFriendId();
    }

    @Transactional
    public void deleteFriend(Long friendId){
        if(!friendRepository.existsById(friendId)){
            throw new AuthException(AuthErrorCode.FRIEND_NOT_FOUND);
        }
        friendRepository.deleteById(friendId);
    }
}