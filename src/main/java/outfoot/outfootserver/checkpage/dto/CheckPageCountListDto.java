package outfoot.outfootserver.checkpage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckPageCountListDto (
        @Schema(description = "도장판 아이디", example = "1") @NotNull Long total,
        @Schema(description = "도장판 아이디", example = "1") List<CheckPageListResponse> checkPages
        ) {

        public CheckPageCountListDto(@NotNull Long total, List<CheckPageListResponse> checkPages) {
                this.total = total;
                this.checkPages = checkPages;
        }
}
