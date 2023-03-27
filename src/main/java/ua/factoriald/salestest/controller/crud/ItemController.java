package ua.factoriald.salestest.controller.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.factoriald.salestest.controller.abstraction.AbstractController;
import ua.factoriald.salestest.entity.Item;
import ua.factoriald.salestest.service.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController extends AbstractController<Item, ItemService> {

    protected ItemController(ItemService service) {
        super(service);
    }

    /**
     * @return all entity records
     */
    @GetMapping("/all")
    ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    /**
     * creates new entity
     * @param newItem new entity object
     * @return new created entity
     */
    @PostMapping("/new")
    ResponseEntity<Item> newItem(@RequestBody Item newItem) {
        Optional<Item> result = getService().save(newItem);
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
    ResponseEntity<Item> getItem(@PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Item> result = getService().getById(id.get());
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    /**
     * Updates entity
     * @param newItem updated entity
     * @param id id of entity for update
     * @return updated entity
     */
    @PutMapping("/{id}")
    ResponseEntity<Item> updateItem(@RequestBody Item newItem, @PathVariable Optional<Long> id) {

        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Item> neededItem = getService().getById(id.get());
        if(neededItem.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        neededItem.get().setName(newItem.getName());
        neededItem.get().setPrice(newItem.getPrice());
        getService().getRepository().flush();

        Optional<Item> result = getService().getById(id.get());

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
    ResponseEntity<Item> deleteItem(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Item> result = getService().getById(id.get());
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
