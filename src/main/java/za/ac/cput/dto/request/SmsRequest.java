package za.ac.cput.dto.request;

import za.ac.cput.enums.SmsStatus;
import jakarta.validation.constraints.NotNull;

public record SmsRequest(
        @NotNull(message = "customerId is required") Integer customerId,
        Integer saleId,
        String phoneNumber,
        String message,
        String smsType,
        SmsStatus status
) {
}
