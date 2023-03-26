package ua.factoriald.salestest.repository;

import org.springframework.stereotype.Repository;
import ua.factoriald.salestest.entity.Payment;
import ua.factoriald.salestest.repository.abstraction.CommonRepository;

@Repository
public interface PaymentRepository extends CommonRepository<Payment> {
}
