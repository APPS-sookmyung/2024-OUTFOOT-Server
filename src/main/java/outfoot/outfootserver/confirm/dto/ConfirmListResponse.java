package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

@Builder
public record ConfirmListResponse(
        @Schema(description = "사진 설명", example = "메모") String memo,
        @Schema(description = "도장판 내 인증판 순서", example = "1") Long order) {

    public static ConfirmListResponse toConfirmList(Confirm confirm) {
        return ConfirmListResponse.builder()
                .memo(confirm.getMemo())
//                .date(confirm.getDate())
                .order(confirm.getOrder())
                .build();
    }
}
