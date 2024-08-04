package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

public record ConfirmRequest(
        @Schema(description = "사진 설명", example = "메모") String memo,
        @Schema(description = "도장판 아이디", example = "1") Long checkPageId,
        @Schema(description = "이미지 url", example = "image/png") MultipartFile image) {

    public static Confirm toConfirm(ConfirmRequest dto, Long order, CheckPage checkPage, String imageUrl){
        return Confirm.builder()
                .memo(dto.memo())
                .imageUrl(imageUrl)
                .order(order)
                .checkPage(checkPage)
                .build();
    }

}
