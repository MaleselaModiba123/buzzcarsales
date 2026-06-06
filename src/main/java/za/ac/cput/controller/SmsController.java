package za.ac.cput.controller;

import za.ac.cput.dto.request.SmsRequest;
import za.ac.cput.dto.response.SmsResponse;
import za.ac.cput.mapper.SmsMapper;
import za.ac.cput.service.SmsService;
import jakarta.validation.Valid;
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
    private final SmsMapper smsMapper;

    @PostMapping("/create")
    public ResponseEntity<SmsResponse> create(@Valid @RequestBody SmsRequest request) {
        return ResponseEntity.ok(smsMapper.toResponse(smsService.save(smsMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SmsResponse> read(@PathVariable Integer id) {
        return smsService.getById(id)
                .map(smsMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SmsResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(smsService.getAll(pageable).map(smsMapper::toResponse));
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<List<SmsResponse>> getByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(smsService.getByCustomerId(customerId).stream().map(smsMapper::toResponse).toList());
    }

    @GetMapping("/bySaleId/{saleId}")
    public ResponseEntity<List<SmsResponse>> getBySaleId(@PathVariable Integer saleId) {
        return ResponseEntity.ok(smsService.getBySale(saleId).stream().map(smsMapper::toResponse).toList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        smsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
