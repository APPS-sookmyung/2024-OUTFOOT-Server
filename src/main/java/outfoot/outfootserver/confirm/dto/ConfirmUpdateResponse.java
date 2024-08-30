package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

@Builder
public record ConfirmUpdateResponse(
        @Schema(description = "제목", example = "하루에 물 2리터 마시기") String title,
        @Schema(description = "한 줄 소개", example = "건강한 이너뷰티") String intro,
        @Schema(description = "이미지", example = "image/png") MultipartFile image) {

    public static ConfirmUpdateResponse toConfirm(Confirm confirm){
        return ConfirmUpdateResponse.builder()
                .title(confirm.getTitle())
                .intro(confirm.getContent())
                .build();
    }

}
