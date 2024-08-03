package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

public record ConfirmRequest(
        @Schema(description = "사진 설명", example = "메모") String memo,
        @Schema(description = "도장판 아이디", example = "1") Long checkPageId) {

    public static Confirm toConfirm(ConfirmRequest dto, Long order, CheckPage checkPage){
        return Confirm.builder()
                .memo(dto.memo())
                .order(order)
                .checkPage(checkPage)
                .build();
    }

}
