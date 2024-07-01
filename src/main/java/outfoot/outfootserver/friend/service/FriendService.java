package outfoot.outfootserver.friend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addFriend(Member from_member,Member to_member){
        if (from_member.equals(to_member))
            throw new AuthException(AuthErrorCode.NOT_FRINED_SELF);
        if (friendRepository.findFriend(from_member, to_member).isPresent())
            throw new AuthException(AuthErrorCode.FRIEND_DUPLICATED);

        Friend newFriend = Friend.builder()
                .fromMember(from_member)
                .toMember(to_member)
                .nickname(from_member.getNickname() + " - " + to_member.getNickname())
                .build();

        friendRepository.save(newFriend);
        return newFriend.getFriendId();
    }

    @Transactional
    public void deleteFriend(Long friendId){
        if(!friendRepository.existsById(friendId)){
            throw new AuthException(AuthErrorCode.FRIEND_NOT_FOUND);
        }
        friendRepository.deleteById(friendId);
    }

    @Transactional(readOnly = true)
    public Member searchFriend(String searchCode) {
        Member member = memberRepository.findByCode(searchCode)
                .orElseThrow(()->new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));

        return member;
    }
}