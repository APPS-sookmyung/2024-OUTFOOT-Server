package outfoot.outfootserver.confirm.dto;

import lombok.Builder;
import lombok.Getter;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;
@Builder
public record ConfirmResponse(String memo, String createdAt, Long order, Long checkPageId) {

    public static ConfirmResponse toConfirm(Confirm confirm){
        return ConfirmResponse.builder()
                .memo(confirm.getMemo())
                .createdAt(confirm.getCreatedAt())
                .order(confirm.getOrder())
                .checkPageId(confirm.getCheckPage().getId())
                .build();
    }
}
