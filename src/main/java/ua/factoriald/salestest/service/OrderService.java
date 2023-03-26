package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.OrderStatusEnums;
import ua.factoriald.salestest.entity.SalesOrder;
import ua.factoriald.salestest.repository.OrderRepository;
import ua.factoriald.salestest.service.abstraction.AbstractService;

@Service
public class OrderService extends AbstractService<SalesOrder, OrderRepository> {

    private OrderStatusService orderStatusService;
    public OrderService(OrderRepository repository, OrderStatusService orderStatusService) {
        super(repository);
        this.orderStatusService = orderStatusService;
    }

    public void setStatusAsCreated(SalesOrder newPayment) {
        newPayment.setStatus(this.orderStatusService.getById(OrderStatusEnums.CREATED.getStatusId()).get());
    }


}
