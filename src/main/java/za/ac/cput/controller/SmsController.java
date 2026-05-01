package za.ac.cput.controller;

import za.ac.cput.domain.Sms;
import za.ac.cput.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/create")
    public ResponseEntity<Sms> create(@RequestBody Sms sms) {
        return ResponseEntity.ok(smsService.save(sms));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Sms> read(@PathVariable Integer id) {
        return smsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Sms>> getAll(Pageable pageable) {
        return ResponseEntity.ok(smsService.getAll(pageable));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Sms> getById(@PathVariable Integer id) {
        return smsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<List<Sms>> getByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(smsService.getByCustomerId(customerId));
    }

    @GetMapping("/bySaleId/{saleId}")
    public ResponseEntity<List<Sms>> getBySaleId(@PathVariable Integer saleId) {
        return ResponseEntity.ok(smsService.getBySale(saleId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        smsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
