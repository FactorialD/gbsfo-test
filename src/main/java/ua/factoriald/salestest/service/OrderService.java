package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.SalesOrder;
import ua.factoriald.salestest.repository.OrderRepository;
import ua.factoriald.salestest.service.abstraction.AbstractService;

@Service
public class OrderService extends AbstractService<SalesOrder, OrderRepository> {

    public OrderService(OrderRepository repository) {
        super(repository);
    }
}
