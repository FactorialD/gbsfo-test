package ua.factoriald.salestest.service;

import org.springframework.stereotype.Service;
import ua.factoriald.salestest.entity.Item;
import ua.factoriald.salestest.repository.ItemRepository;

@Service
public class ItemService extends AbstractService<Item, ItemRepository> {

    public ItemService(ItemRepository repository) {
        super(repository);
    }
}
