package outfoot.outfootserver.friend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.friend.domain.Friend;

import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.dto.SearchFriendRequest;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.member.Member;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;


    // 걍 처음부터 잘못됐어............... 다시 손 보자.................
    @Transactional
    public Long addFriend(AddFriendRequest dto, Long memberId){
        Member member = memberRepository.findByUsername(memberId).orElseThrow(()-> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));

        Member friendMember = memberRepository = memberRepository.findByCode(dto.getFriendCode()).orElseThrow(()-> new AuthException(AuthErrorCode.FRIEND_MEMBER_NOT_FOUND));

        Friend friend = new Friend();
        friend.setMember(member);
        friend.setNickname(dto.getNickname());
        friendRepository.save(friend);
        return friend.getFriend_id();
    }

    @Transactional
    public void deleteFriend(Long friendId){

        if(!friendRepository.existsById(friendId)){
            throw new AuthException(AuthErrorCode.FRIEND_NOT_FOUND);
        }
        friendRepository.deleteById(friendId);
    }

    public Friend searchFriend(SearchFriendRequest dto, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
        return friendRepository.findByMemberAndFriendId(member, dto.getFriendCode()).orElseThrow(()-> new AuthException(AuthErrorCode.FRIEND_NOT_FOUND));
    }
}