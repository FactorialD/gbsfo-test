package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.Item;
import ua.factoriald.salestest.entity.OrderStatusEnums;
import ua.factoriald.salestest.entity.Payment;
import ua.factoriald.salestest.entity.SalesOrder;
import ua.factoriald.salestest.repository.OrderRepository;
import ua.factoriald.salestest.service.abstraction.AbstractService;

@Service
public class OrderService extends AbstractService<SalesOrder, OrderRepository> {

    private final OrderStatusService orderStatusService;
    public OrderService(OrderRepository repository, OrderStatusService orderStatusService) {
        super(repository);
        this.orderStatusService = orderStatusService;
    }

    public void setStatusAsCreated(SalesOrder order) {
        order.setStatus(this.orderStatusService.getById(OrderStatusEnums.CREATED.getStatusId()).get());
    }

    public void setStatusAsProcessing(SalesOrder order) {
        order.setStatus(this.orderStatusService.getById(OrderStatusEnums.PROCESSING.getStatusId()).get());
    }

    public void setStatusAsShipping(SalesOrder order) {
        order.setStatus(this.orderStatusService.getById(OrderStatusEnums.SHIPPING.getStatusId()).get());
    }

    public void setStatusAsDelivered(SalesOrder order) {
        order.setStatus(this.orderStatusService.getById(OrderStatusEnums.DELIVERED.getStatusId()).get());
    }

    /**
     * Returns true if sum of payment > sum of items
     * @param salesOrder
     * @return
     */
    public boolean isOrderPayed(SalesOrder salesOrder) {
        Float needToPay = 0f;
        for (Item item :salesOrder.getTotalItems()
             ) {
            needToPay += item.getPrice();
        }
        Float payed = 0f;
        for (Payment payment :salesOrder.getTotalPayments()
        ) {
            payed += payment.getSum();
        }
        return payed >= needToPay;
    }
}
