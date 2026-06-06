package za.ac.cput.dto.response;

import za.ac.cput.enums.SmsStatus;
import java.time.LocalDateTime;

public record SmsResponse(
        Integer smsId,
        String phoneNumber,
        String message,
        String smsType,
        LocalDateTime sentAt,
        SmsStatus status,
        Integer customerId,
        Integer saleId
) {
}
