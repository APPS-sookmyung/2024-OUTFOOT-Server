package outfoot.outfootserver.checkpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.Animal;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.CheckPageCountListDto;
import outfoot.outfootserver.checkpage.dto.CheckPageListResponse;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckPageService {
    private final CheckPageRepository checkPageRepository;

    @Transactional
    public CheckPageResponse saveCheckPage (CheckPageRequest dto, Member member) {

        // animal_type 찾았는데 없으면 오류 (Animal 클래스 예외 전파), 있으면 Animal 반환
        Animal animal = Animal.of(dto.animalId());

        CheckPage checkPage = checkPageRepository.save(CheckPageRequest.toCheckPage(dto, animal.getAnimalName(), member));

        return CheckPageResponse.toCheckPage(checkPage);

    }

    public CheckPageCountListDto findAllCheckPage(Member member) {

//        여기만 하면 됨
        List<CheckPage> checkPageList = checkPageRepository.findAllById(member);

        // 엔티티 -> DTO
        List<CheckPageListResponse> checkPageDtoList = checkPageList.stream()
                .map(CheckPageListResponse::toCheckPageList)
                .toList();

        return new CheckPageCountListDto(checkPageRepository.count(), checkPageDtoList);
    }

    public CheckPageResponse findCheckPage(Long checkPageId, Member member) {
        CheckPage checkPage = findByIdAndMember(checkPageId, member);
        return CheckPageResponse.toCheckPage(checkPage);
    }


    @Transactional
    public Long deleteCheckPage(Long checkPageId, Member member) {
        CheckPage checkPage = findByIdAndMember(checkPageId, member);

        if (checkPage.getMember() != member) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }
        checkPageRepository.delete(checkPage);
        return checkPageId;
    }

    public CheckPage findByIdAndMember(Long checkPageId, Member member) {
        return checkPageRepository.findByIdAndMember(checkPageId, member)
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));
    }

    public CheckPage findById(Long checkPageId) {
        return checkPageRepository.findById(checkPageId)
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));

    }
}
