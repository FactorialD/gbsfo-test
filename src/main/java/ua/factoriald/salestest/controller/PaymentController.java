package ua.factoriald.salestest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.factoriald.salestest.controller.abstraction.AbstractController;
import ua.factoriald.salestest.entity.Payment;
import ua.factoriald.salestest.service.PaymentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController extends AbstractController<Payment, PaymentService> {

    protected PaymentController(PaymentService service) {
        super(service);
    }

    /**
     * @return all entity records
     */
    @GetMapping("/all")
    ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    /**
     * creates new entity
     * @param newPayment new entity object
     * @return new created entity
     */
    @PostMapping("/new")
    ResponseEntity<Payment> newItem(@RequestBody Payment newPayment) {
        Optional<Payment> result = getService().save(newPayment);
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
    ResponseEntity<Payment> getItem(@PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Payment> result = getService().getById(id.get());
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
    ResponseEntity<Payment> updateItem(@RequestBody Payment newPayment, @PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Payment> neededPayment = getService().getById(id.get());
        if(neededPayment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        neededPayment.get().setNumber(newPayment.getNumber());
        neededPayment.get().setSum(newPayment.getSum());
        neededPayment.get().setPaymentDate(newPayment.getPaymentDate());

        getService().getRepository().flush();

        Optional<Payment> result = getService().getById(id.get());

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
    ResponseEntity<Payment> deleteItem(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Payment> result = getService().getById(id.get());
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

