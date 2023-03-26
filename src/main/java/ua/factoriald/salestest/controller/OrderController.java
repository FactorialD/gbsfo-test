package ua.factoriald.salestest.controller;

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
     * creates new entity
     * @param newPayment new entity object
     * @return new created entity
     */
    @PostMapping("/new")
    ResponseEntity<SalesOrder> newItem(@RequestBody SalesOrder newPayment) {
        Optional<SalesOrder> result = getService().save(newPayment);
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
    ResponseEntity<SalesOrder> getItem(@PathVariable Optional<Long> id) {

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
     * @param newPayment updated entity
     * @param id id of entity for update
     * @return updated entity
     */
    @PutMapping("/{id}")
    ResponseEntity<SalesOrder> updateItem(@RequestBody SalesOrder newPayment, @PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<SalesOrder> neededController = getService().getById(id.get());
        if(neededController.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        neededController.get().setNumber(newPayment.getNumber());
        neededController.get().setStatus(newPayment.getStatus());
        neededController.get().setTotalItems(newPayment.getTotalItems());
        neededController.get().setTotalPayments(newPayment.getTotalPayments());

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
    ResponseEntity<SalesOrder> deleteItem(@PathVariable Optional<Long> id) {
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


