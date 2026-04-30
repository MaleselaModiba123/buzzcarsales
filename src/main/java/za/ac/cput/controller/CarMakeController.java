package za.ac.cput.controller;

import com.buzzcar.sales.entity.CarMake;
import com.buzzcar.sales.service.CarMakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/carmakes")
public class CarMakeController {
    private final CarMakeService carMakeService;

    @PostMapping("/create")
    public ResponseEntity<CarMake> create(@RequestBody CarMake carMake) {
        return ResponseEntity.ok(carMakeService.save(carMake));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarMake> read(@PathVariable Integer id) {
        return carMakeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CarMake>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carMakeService.getAll(pageable));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CarMake> getById(@PathVariable Integer id) {
        return carMakeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarMake> update(@PathVariable Integer id, @RequestBody CarMake carMake) {
        return ResponseEntity.ok(carMakeService.update(id, carMake));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carMakeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
