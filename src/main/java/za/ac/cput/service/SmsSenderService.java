package za.ac.cput.service;


import za.ac.cput.domain.*;
import za.ac.cput.enums.SmsStatus;
import za.ac.cput.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsSenderService {
    private final SmsRepository smsRepository;

    @Value("${smsportal.api.key}")
    private String apiKey;

    @Value("${smsportal.api.secret}")
    private String apiSecret;

    @Value("${smsportal.api.url}")
    private String apiUrl;

    /**
     * Sends the sale-confirmation SMS asynchronously so a slow or failing
     * SMSPortal call never blocks or rolls back the sale transaction.
     */
    @Async
    public void sendSaleConfirmation(Sale sale) {
        Customer customer = sale.getCustomer();
        String message = "Dear " + customer.getFirstName() +
                ", your purchase of a " +
                sale.getCar().getCarModel().getCarMake().getMakeName() + " " +
                sale.getCar().getCarModel().getModelName() +
                " has been confirmed. Total: R" + sale.getSalePrice() +
                ". Thank you for choosing Buzz Car Sales!";

        sendAndLog(customer, sale, message);
    }

    private void sendAndLog(Customer customer, Sale sale, String message) {
        SmsStatus smsStatus;

        try {
            String credentials = apiKey + ":" + apiSecret;
            String base64Credentials = Base64.getUrlEncoder()
                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            String requestBody = "{ \"messages\" : [ { \"content\" : \"" +
                    message + "\", \"destination\" : \"" +
                    customer.getPhoneNumber() + "\" } ] }";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Authorization", "Basic " + base64Credentials)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            smsStatus = response.statusCode() == 200
                    ? SmsStatus.SENT
                    : SmsStatus.FAILED;

            if (smsStatus == SmsStatus.FAILED) {
                log.warn("SMSPortal returned status {} for sale {}",
                        response.statusCode(), sale.getSaleId());
            }

        } catch (Exception e) {
            smsStatus = SmsStatus.FAILED;
            log.error("Failed to send confirmation SMS for sale {}", sale.getSaleId(), e);
        }

        Sms smsLog = new Sms();
        smsLog.setCustomer(customer);
        smsLog.setSale(sale);
        smsLog.setPhoneNumber(customer.getPhoneNumber());
        smsLog.setMessage(message);
        smsLog.setSentAt(LocalDateTime.now());
        smsLog.setStatus(smsStatus);

        smsRepository.save(smsLog);
    }
}
