package za.ac.cput.controller;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.PdfReportService;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class PdfReportController {

    private final PdfReportService pdfReportService;

    @GetMapping("/sales")
    public ResponseEntity<byte[]> salesReport() throws DocumentException {
        byte[] pdf = pdfReportService.generateSalesReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=sales_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/available-cars")
    public ResponseEntity<byte[]> availableCarsReport() throws DocumentException {
        byte[] pdf = pdfReportService.generateAvailableCarsReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=available_cars_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/employee-performance")
    public ResponseEntity<byte[]> employeePerformanceReport() throws DocumentException {
        byte[] pdf = pdfReportService.generateEmployeePerformanceReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=employee_performance_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}