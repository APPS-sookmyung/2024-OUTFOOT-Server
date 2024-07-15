package outfoot.outfootserver.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.service.CheckPageService;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.service.ConfirmService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class LikeManageService {

    private final CheckPageService checkPageService;
    private final ConfirmService confirmService;
    private final MemberService memberService;

    public Member loadMember (Long member_id) {
        return memberService.loadMember(member_id);
    }

    public CheckPage loadCheckPage(Long checkPageId) {
        return checkPageService.findById(checkPageId);
    }

    public Confirm loadConfirm(Long confirmId) {
        return confirmService.findById(confirmId);
    }
}
