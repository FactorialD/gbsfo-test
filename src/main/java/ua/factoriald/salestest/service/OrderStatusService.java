package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.OrderStatus;
import ua.factoriald.salestest.repository.OrderStatusRepository;
import ua.factoriald.salestest.service.abstraction.AbstractService;

@Service
public class OrderStatusService extends AbstractService<OrderStatus, OrderStatusRepository> {

    public OrderStatusService(OrderStatusRepository repository) {
        super(repository);
    }
}
