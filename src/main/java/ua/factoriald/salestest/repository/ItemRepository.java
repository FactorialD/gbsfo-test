package ua.factoriald.salestest.repository;

import org.springframework.stereotype.Repository;
import ua.factoriald.salestest.entity.Item;

@Repository
public interface ItemRepository extends CommonRepository<Item> {
}
