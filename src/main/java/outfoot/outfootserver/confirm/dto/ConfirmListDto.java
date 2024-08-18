package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ConfirmListDto(
        @Schema(description = "인증판 총 개수", example = "1") Long total,
        @Schema(description = "도장판 메이트", example = "고양이") String animal,
        @Schema(description = "인증판 리스트") List<ConfirmListResponse> confirms) {

    public ConfirmListDto(Long total,  String animal, List<ConfirmListResponse> confirms){
        this.total = total;
        this.animal = animal;
        this.confirms = confirms;
    }
}
