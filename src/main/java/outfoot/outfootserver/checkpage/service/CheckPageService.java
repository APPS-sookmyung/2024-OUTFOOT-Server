package outfoot.outfootserver.checkpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.checkpage.domain.Animal;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;
import outfoot.outfootserver.member.domain.Member;

import java.util.Arrays;

import static org.hibernate.event.spi.EventType.values;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckPageService {
    private final CheckPageRepository checkPageRepository;

    @Transactional
//    public Long saveCheckPage (Member member, CheckPageRequest dto) {
    public Long saveCheckPage (CheckPageRequest dto) {
        checkPageRepository.findByTitle(dto.title()).ifPresent(e -> {
            throw new CheckPageException(CheckPageErrorCode.CHECKPAGE_DUPLICATION);
        });

        // animal_type 찾았는데 없으면 오류 (Animal 클래스 예외 전파), 있으면 Animal 반환
        Animal animal = Animal.of(dto.animalId());

//        CheckPage checkPage = checkPageRepository.save(CheckPageRequest.toCheckPage(member, dto));
        CheckPage checkPage = checkPageRepository.save(CheckPageRequest.toCheckPage(dto, animal));
        return checkPage.getId();
    }
}
