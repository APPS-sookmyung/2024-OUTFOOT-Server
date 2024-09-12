package outfoot.outfootserver.checkpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.Animal;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.*;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.ConfirmImageResponse;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.repository.ConfirmRepository;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckPageService {
    private final CheckPageRepository checkPageRepository;
    private final ConfirmRepository confirmRepository;

    @Transactional
    public CheckPageSaveResponse saveCheckPage (CheckPageRequest dto, Member member) {

        // animal_type 찾았는데 없으면 오류 (Animal 클래스 예외 전파), 있으면 Animal 반환
        Animal animal = Animal.of(dto.animalId());

        CheckPage checkPage = checkPageRepository.save(CheckPageRequest.toCheckPage(dto, animal.getAnimalName(), member));
        return CheckPageSaveResponse.toCheckPage(checkPage);

    }

    public CheckPageCountListDto findAllCheckPage(Member member) {

//        여기만 하면 됨
        List<CheckPage> checkPageList = checkPageRepository.findAllById(member);

        // 엔티티 -> DTO
        List<CheckPageListResponse> checkPageDtoList = checkPageList.stream()
                .map(CheckPageListResponse::toCheckPageList)
                .toList();

        return new CheckPageCountListDto(checkPageList.size(), checkPageDtoList);
    }

    public CheckPageResponse findCheckPage(Long checkPageId) {
        CheckPage checkPage = findById(checkPageId);

        List<Confirm> confirms = confirmRepository.findByCheckPageId(checkPageId);

        List<ConfirmImageResponse> confirmResponses = confirms.stream()
                .map(ConfirmImageResponse::toConfirm)
                .collect(Collectors.toList());
        return CheckPageResponse.toCheckPage(checkPage, confirmResponses);
    }

    @Transactional
    public Long deleteCheckPage(Long checkPageId, Member member) {
        CheckPage checkPage = findById(checkPageId);

        if (checkPage.getMember() != member) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_USER);
        }
        checkPageRepository.delete(checkPage);
        return checkPageId;
    }

    public CheckPage findById(Long checkPageId) {
        return checkPageRepository.findById(checkPageId)
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.CHECKPAGE_NOT_FOUND));
    }
}
