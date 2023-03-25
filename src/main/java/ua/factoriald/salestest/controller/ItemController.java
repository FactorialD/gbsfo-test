package ua.factoriald.salestest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/all")
    ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @PostMapping("/new")
    ResponseEntity<Item> newItem(@RequestBody Item newItem) {
        Optional<Item> result = getService().save(newItem);
        if(result.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(result.get());
    }

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

        Optional<Item> result = getService().save(newItem);
        if(result.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(result.get());
    }

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
