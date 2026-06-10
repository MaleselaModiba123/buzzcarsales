package za.ac.cput.service;

import za.ac.cput.domain.Sale;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SmsSenderService smsSenderService;

    @InjectMocks
    private SaleService saleService;

    @Test
    void save_persistsThenSendsConfirmationSms() {
        Sale sale = new Sale();
        Sale saved = new Sale();
        saved.setSaleId(1);
        when(saleRepository.save(sale)).thenReturn(saved);

        Sale result = saleService.save(sale);

        assertThat(result.getSaleId()).isEqualTo(1);
        verify(saleRepository).save(sale);
        // confirmation SMS is fired for the persisted sale (async in production)
        verify(smsSenderService).sendSaleConfirmation(saved);
    }

    @Test
    void read_throwsNotFound_whenMissing() {
        when(saleRepository.findById(42)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.read(42))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Sale");
    }
}
