package outfoot.outfootserver.friend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.dto.FriendCountListResponse;
import outfoot.outfootserver.friend.dto.FriendListResponse;
import outfoot.outfootserver.friend.exception.FriendErrorCode;
import outfoot.outfootserver.friend.exception.FriendException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional
    public void addFriend(Member fromMember, Member toMember){

        if (fromMember.equals(toMember))
            throw new FriendException(FriendErrorCode.NOT_FRIEND_SELF);

        friendRepository.findFriend(fromMember, toMember)
                .ifPresent(e -> {
                    throw new FriendException(FriendErrorCode.FRIEND_DUPLICATED);
                });

        Friend newFriend = AddFriendRequest.toFriend(fromMember, toMember);
        friendRepository.save(newFriend);
    }

    @Transactional
    public  void deleteFriend(Long friendId, Member member ) { // TODO: 친구 테이블 내 값도 삭제 필요
        Friend friend = friendRepository.findByIdAndMember(friendId, member)
                .orElseThrow(() -> new FriendException(FriendErrorCode.FRIEND_NOT_FOUND));

        friendRepository.delete(friend);
    }

    public FriendCountListResponse findAllFriend(Long memberId){
        List<Friend> friendList = friendRepository.findByMemberId(memberId);

        List<FriendListResponse> friendLists = friendList.stream()
                .map(FriendListResponse::toFriendList)
                .toList();

        return new FriendCountListResponse(friendList.size(), friendLists);
    }
}