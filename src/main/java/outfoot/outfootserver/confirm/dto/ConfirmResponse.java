package outfoot.outfootserver.confirm.dto;

import lombok.Builder;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

@Builder
public record ConfirmResponse(String memo, Date date, Long checkPageId) {

    public static ConfirmResponse toConfirm(Confirm confirm){
        return ConfirmResponse.builder()
                .memo(confirm.getMemo())
                .date(confirm.getDate())
                .checkPageId(confirm.getCheckPage().getId())
                .build();
    }
}
