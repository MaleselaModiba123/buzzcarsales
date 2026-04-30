package za.ac.cput.service;

import za.ac.cput.entity.Customer;
import za.ac.cput.entity.Sale;
import za.ac.cput.entity.Sms;
import za.ac.cput.enums.SmsStatus;
import za.ac.cput.enums.SmsType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class SmsSenderService {
    private final SmsSenderService smsSenderService;

    @Value("${sms.api.url}")
    private String smsApiUrl;

    public void sendSaleConfirmation(Sale sale) {
        Customer customer = sale.getCustomer();
        String message = "Dear " + customer.getFirstName() +
                ", your purchase of a " +
                sale.getCar().getCarModel().getCarMake().getMakeName() + " " +
                sale.getCar().getCarModel().getModelName() +
                " has been confirmed. Total: R" + sale.getSalePrice() +
                ". Thank you for choosing Buzz Car Sales!";

        sendAndLog(customer, sale, message, SmsType.CONFIRMATION);
    }

    private void sendAndLog(Customer customer, Sale sale, String message, SmsType smsType) {
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
                    .uri(new URI("https://rest.smsportal.com/BulkMessages"))
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

        } catch (Exception e) {
            smsStatus = SmsStatus.FAILED;
            e.printStackTrace();
        }
        
        Sms log = new Sms();
        log.setCustomer(customer);
        log.setSale(sale);
        log.setPhoneNumber(customer.getPhoneNumber());
        log.setMessage(message);
        log.setSmsType(smsType);
        log.setSentAt(LocalDateTime.now());
        log.setStatus(smsStatus);

        smsService.save(log);
    }
}
