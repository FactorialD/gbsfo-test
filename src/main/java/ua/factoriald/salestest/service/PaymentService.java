package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.Payment;
import ua.factoriald.salestest.repository.PaymentRepository;
import ua.factoriald.salestest.service.abstraction.AbstractService;

@Service
public class PaymentService extends AbstractService<Payment, PaymentRepository> {

    public PaymentService(PaymentRepository repository) {
        super(repository);
    }
}
