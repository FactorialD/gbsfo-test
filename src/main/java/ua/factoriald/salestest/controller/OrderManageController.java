package ua.factoriald.salestest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.factoriald.salestest.controller.abstraction.AbstractController;
import ua.factoriald.salestest.entity.Item;
import ua.factoriald.salestest.entity.OrderStatusEnums;
import ua.factoriald.salestest.entity.Payment;
import ua.factoriald.salestest.entity.SalesOrder;
import ua.factoriald.salestest.service.ItemService;
import ua.factoriald.salestest.service.OrderService;
import ua.factoriald.salestest.service.PaymentService;

import java.util.Optional;

/**
 * Controller for managing lifecycle of orders
 */
@RestController
@RequestMapping("/order/manage")
public class OrderManageController extends AbstractController<SalesOrder, OrderService> {

    private final ItemService itemService;

    private final PaymentService paymentService;

    protected OrderManageController(OrderService service,
                                    ItemService itemService,
                                    PaymentService paymentService) {
        super(service);
        this.itemService = itemService;
        this.paymentService = paymentService;
    }

    //TODO add checks for status. Only CREATED -> PROCESSING
    /**
     * Set status of order as PROCESSING
     * @param id id of entity needed
     * @return entity with id needed
     */
    @GetMapping("/approve/{id}")
    ResponseEntity<SalesOrder> approveOrder(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        getService().setStatusAsProcessing(result.get());
        getService().save(result.get());
        return ResponseEntity.ok(getService().getById(id.get()).get());
    }

    //TODO add checks for status. Only PROCESSING -> SHIPPING
    /**
     * Set status of order as SHIPPING
     * @param id id of entity needed
     * @return entity with id needed
     */
    @GetMapping("/ship/{id}")
    ResponseEntity<?> shipOrder(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        // if order dont have items
        if(result.get().getTotalItems().size() == 0){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order doesn`t have items!");
        }
        // if order was not paid
        if(!getService().isOrderPayed(result.get())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order was not payed completely!");
        }
        getService().setStatusAsShipping(result.get());
        getService().save(result.get());
        return ResponseEntity.ok(getService().getById(id.get()).get());
    }

    //TODO add checks for status. Only SHIPPING -> DELIVERED
    /**
     * Set status of order as DELIVERED
     * @param id id of entity needed
     * @return entity with id needed
     */
    @GetMapping("/deliver/{id}")
    ResponseEntity<?> deliverOrder(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        // if order dont have items
        if(result.get().getTotalItems().size() == 0){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order doesn`t have items!");
        }
        // if order was not paid
        if(!getService().isOrderPayed(result.get())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order was not payed completely!");
        }
        getService().setStatusAsDelivered(result.get());
        getService().save(result.get());
        return ResponseEntity.ok(getService().getById(id.get()).get());
    }

    /**
     * Adds new item to order, if status is CREATED or PROCESSING
     * @param orderId id of entity needed
     * @return entity with id needed
     */
    @PostMapping("/addItem/{orderId}")
    ResponseEntity<?> addItemToOrder(@RequestBody Item newItem, @PathVariable("orderId") Optional<Long> orderId) {
        if(orderId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(orderId.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(newItem == null){
            return ResponseEntity.badRequest().build();
        }
        if(!result.get().getStatus().getId().equals(OrderStatusEnums.CREATED.getStatusId()) &&
                !result.get().getStatus().getId().equals(OrderStatusEnums.PROCESSING.getStatusId())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order already shipped!");
        }
        newItem.setOrder(result.get());
        Item newItemFromDB = itemService.save(newItem).get();
        result.get().getTotalItems().add(newItemFromDB);
        getService().getRepository().saveAndFlush(result.get());
        return ResponseEntity.ok(result);
    }

    /**
     * Removes item from order, if status is CREATED or PROCESSING
     * @param orderId id of entity needed
     * @return entity with id needed
     */
    @DeleteMapping("/removeItem/{orderId}")
    ResponseEntity<?> removeItemFromOrder(@RequestBody Item item, @PathVariable("orderId") Optional<Long> orderId) {
        if(orderId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(orderId.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(item == null){
            return ResponseEntity.badRequest().build();
        }

        if(!result.get().getStatus().getId().equals(OrderStatusEnums.CREATED.getStatusId()) &&
                !result.get().getStatus().getId().equals(OrderStatusEnums.PROCESSING.getStatusId())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order already shipped!");
        }
        Item itemFromDB = itemService.save(item).get();
        itemService.deleteById(item.getId());
        return ResponseEntity.ok(result);
    }

    /**
     * Adds new payment to order, if status is CREATED or PROCESSING
     * @param orderId id of entity needed
     * @return entity with id needed
     */
    @PostMapping("/addPayment/{orderId}")
    ResponseEntity<?> addPaymentToOrder(@RequestBody Payment newPayment, @PathVariable("orderId") Optional<Long> orderId) {
        if(orderId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(orderId.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(newPayment == null){
            return ResponseEntity.badRequest().build();
        }
        if(!result.get().getStatus().getId().equals(OrderStatusEnums.CREATED.getStatusId()) &&
                !result.get().getStatus().getId().equals(OrderStatusEnums.PROCESSING.getStatusId())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order already shipped!");
        }
        newPayment.setOrder(result.get());
        Payment newPaymentFromDB = paymentService.save(newPayment).get();
        result.get().getTotalPayments().add(newPaymentFromDB);
        getService().getRepository().saveAndFlush(result.get());
        return ResponseEntity.ok(result);
    }

}
