package outfoot.outfootserver.confirm.dto;

import lombok.Builder;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

@Builder
public record ConfirmListResponse(String memo, Date date, int order) {

    public static ConfirmListResponse toConfirmList(Confirm confirm) {
        return ConfirmListResponse.builder()
                .memo(confirm.getMemo())
                .date(confirm.getDate())
                .order(confirm.getOrder())
                .build();
    }
}
