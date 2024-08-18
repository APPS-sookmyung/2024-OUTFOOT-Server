package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ConfirmListDto(
        @Schema(description = "인증판 총 개수", example = "1") Long total,
        @Schema(description = "인증판 리스트") List<ConfirmListResponse> confirms) {

    public ConfirmListDto(Long total, List<ConfirmListResponse> confirms){
        this.total = total;
        this.confirms = confirms;
    }
}
