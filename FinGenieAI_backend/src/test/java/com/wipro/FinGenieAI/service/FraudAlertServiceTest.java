package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.FraudAlertDTO;
import com.wipro.FinGenieAI.entity.FraudAlert;
import com.wipro.FinGenieAI.entity.Transaction;
import com.wipro.FinGenieAI.enums.RiskLevel;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.FraudAlertRepository;
import com.wipro.FinGenieAI.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FraudAlertServiceImplTest {

    @Mock
    private FraudAlertRepository fraudAlertRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FraudAlertServiceImpl fraudAlertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ HIGH RISK TEST (> 50000)
    @Test
    void testCheckFraud_HighRisk() {

        Transaction tx = new Transaction();
        tx.setId(1L);
        tx.setAmount(60000.0);

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(tx));

        when(fraudAlertRepository.save(any(FraudAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FraudAlertDTO result = fraudAlertService.checkFraud(1L);

        assertNotNull(result);
        assertEquals(RiskLevel.HIGH, result.getRiskLevel());
        assertTrue(result.isFlagged());
    }

    // ✅ MEDIUM RISK TEST (> 20000)
    @Test
    void testCheckFraud_MediumRisk() {

        Transaction tx = new Transaction();
        tx.setId(2L);
        tx.setAmount(30000.0);

        when(transactionRepository.findById(2L))
                .thenReturn(Optional.of(tx));

        when(fraudAlertRepository.save(any(FraudAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FraudAlertDTO result = fraudAlertService.checkFraud(2L);

        assertNotNull(result);
        assertEquals(RiskLevel.MEDIUM, result.getRiskLevel());
        assertTrue(result.isFlagged());
    }

    // ✅ LOW RISK TEST
    @Test
    void testCheckFraud_LowRisk() {

        Transaction tx = new Transaction();
        tx.setId(3L);
        tx.setAmount(5000.0);

        when(transactionRepository.findById(3L))
                .thenReturn(Optional.of(tx));

        when(fraudAlertRepository.save(any(FraudAlert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FraudAlertDTO result = fraudAlertService.checkFraud(3L);

        assertNotNull(result);
        assertEquals(RiskLevel.LOW, result.getRiskLevel());
        assertFalse(result.isFlagged());
    }

    // ✅ TRANSACTION NOT FOUND
    @Test
    void testCheckFraud_TransactionNotFound() {

        when(transactionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            fraudAlertService.checkFraud(1L);
        });
    }
}