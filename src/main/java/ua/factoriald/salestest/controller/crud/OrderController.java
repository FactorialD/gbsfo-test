package ua.factoriald.salestest.controller.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.factoriald.salestest.controller.abstraction.AbstractController;
import ua.factoriald.salestest.entity.SalesOrder;
import ua.factoriald.salestest.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController extends AbstractController<SalesOrder, OrderService> {

    protected OrderController(OrderService service) {
        super(service);
    }

    /**
     * @return all entity records
     */
    @GetMapping("/all")
    ResponseEntity<List<SalesOrder>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    /**
     * creates new Order
     * @param newOrder new Order object
     * @return new created Order
     */
    @PostMapping("/new")
    ResponseEntity<SalesOrder> newOrder(@RequestBody SalesOrder newOrder) {
        getService().setStatusAsCreated(newOrder);
        Optional<SalesOrder> result = getService().save(newOrder);
        if(result.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(result.get());
    }

    /**
     * @param id id of entity needed
     * @return entity with id needed
     */
    @GetMapping("/{id}")
    ResponseEntity<SalesOrder> getOrder(@PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    /**
     * Updates entity
     * @param newOrder updated entity
     * @param id id of entity for update
     * @return updated entity
     */
    @PutMapping("/{id}")
    ResponseEntity<SalesOrder> updateOrder(@RequestBody SalesOrder newOrder, @PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> neededOrder = getService().getById(id.get());
        if(neededOrder.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        neededOrder.get().setNumber(newOrder.getNumber());
        neededOrder.get().setStatus(newOrder.getStatus());
        neededOrder.get().setTotalItems(newOrder.getTotalItems());
        neededOrder.get().setTotalPayments(newOrder.getTotalPayments());

        getService().getRepository().flush();

        Optional<SalesOrder> result = getService().getById(id.get());

        if(result.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(result.get());
    }

    /**
     * Deletes entity with given id
     * @param id entity id to delete
     * @return empty 204 response
     */
    @DeleteMapping("/{id}")
    ResponseEntity<SalesOrder> deleteOrder(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Boolean deleteResult = getService().deleteById(id.get());
        if(deleteResult){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.internalServerError().build();
    }

}


