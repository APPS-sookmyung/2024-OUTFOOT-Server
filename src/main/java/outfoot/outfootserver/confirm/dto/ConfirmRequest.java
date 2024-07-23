package outfoot.outfootserver.confirm.dto;

import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.Date;

public record ConfirmRequest(String memo, Date date, Long checkPageId) {

    public static Confirm toConfirm(ConfirmRequest dto, CheckPage checkPage){
        return Confirm.builder()
                .memo(dto.memo())
                .date(dto.date())
                .checkPage(checkPage)
                .build();
    }

}
