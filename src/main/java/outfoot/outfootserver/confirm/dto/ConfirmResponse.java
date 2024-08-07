package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;
@Builder
public record ConfirmResponse(
        @Schema(description = "사진 설명", example = "메모") String memo,
        @Schema(description = "생성 일자", example = "2024-08-03 15:51:46") String createdAt,
        @Schema(description = "도장판 내 인증판 순서", example = "1") Long order,
        @Schema(description = "도장판 아이디", example = "1") Long checkPageId,
        @Schema(description = "인정 개수", example = "1") long likeCount,
        @Schema(description = "부정 개수", example = "0") long dislikeCount,
        @Schema(description = "이미지 주소", example = "img/png") String imageUrl) {

    public static ConfirmResponse toConfirm(Confirm confirm, long likeCount, long dislikeCount, String imageUrl){
        return ConfirmResponse.builder()
                .memo(confirm.getMemo())
                .createdAt(confirm.getCreatedAt())
                .order(confirm.getOrder())
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .checkPageId(confirm.getCheckPage().getId())
                .imageUrl(imageUrl)
                .build();
    }
}
