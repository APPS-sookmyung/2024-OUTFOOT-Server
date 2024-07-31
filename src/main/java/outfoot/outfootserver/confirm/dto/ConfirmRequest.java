package outfoot.outfootserver.confirm.dto;

import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

public record ConfirmRequest(String memo, Long checkPageId) {

    public static Confirm toConfirm(ConfirmRequest dto, Long order, CheckPage checkPage){
        return Confirm.builder()
                .memo(dto.memo())
                .order(order)
                .checkPage(checkPage)
                .build();
    }

}
