package outfoot.outfootserver.friend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.dto.FriendListResponse;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addFriend(Member fromMember,Member toMember){
        if (fromMember.equals(toMember))
            throw new AuthException(AuthErrorCode.NOT_FRINED_SELF);
        if (friendRepository.findFriend(fromMember, toMember).isPresent())
            throw new AuthException(AuthErrorCode.FRIEND_DUPLICATED);

        AddFriendRequest dto = new AddFriendRequest(fromMember, toMember);
        Friend newFriend = dto.toFriend();

        friendRepository.save(newFriend);
        return newFriend.getFriendId();
    }

    @Transactional
    public  void deleteFriend(Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.FRIEND_NOT_FOUND));

        friendRepository.delete(friend);
    }

    @Transactional(readOnly = true)
    public Member searchFriend(String searchCode) {
        Member member = memberRepository.findByCode(searchCode)
                .orElseThrow(()->new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

    public List<FriendListResponse> findAllFriend(Long memberId){
        List<Friend> friendList = friendRepository.findByMemberId(memberId);

        return friendList.stream()
                .map(FriendListResponse::toFriendList)
                .toList();
    }
}