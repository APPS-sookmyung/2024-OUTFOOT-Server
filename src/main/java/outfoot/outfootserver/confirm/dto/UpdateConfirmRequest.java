package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateConfirmRequest(
        @Schema(description = "사진 설명", example = "메모") String memo) {
}
