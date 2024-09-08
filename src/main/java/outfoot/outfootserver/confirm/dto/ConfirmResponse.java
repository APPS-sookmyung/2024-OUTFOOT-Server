package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;
@Builder
public record ConfirmResponse(
        @Schema(description = "인증판 아이디", example = "1") @NotNull Long id,
        @Schema(description = "메모 (제목)", example = "오늘도 뿌듯한 하루 ~~") String title,
        @Schema(description = "메모 (내용)", example = "마치 하마가 된 거 같고, 뿌듯함ㅋㅋ") String content,
        @Schema(description = "생성 일자", example = "2024-08-03 15:51:46") String createdAt,
        @Schema(description = "인정 개수", example = "1") long likeCount,
        @Schema(description = "부정 개수", example = "0") long dislikeCount,
        @Schema(description = "이미지", example = "img/png") String imageUrl) {

    public static ConfirmResponse toConfirm(Confirm confirm, long likeCount, long dislikeCount, String imageUrl){
        return ConfirmResponse.builder()
                .id(confirm.getId())
                .title(confirm.getTitle())
                .content(confirm.getContent())
                .createdAt(confirm.getCreatedAt())
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .imageUrl(imageUrl)

                .build();
    }
}
