package ua.factoriald.salestest.repository;

import org.springframework.stereotype.Repository;
import ua.factoriald.salestest.entity.Item;
import ua.factoriald.salestest.repository.abstraction.CommonRepository;

@Repository
public interface ItemRepository extends CommonRepository<Item> {
}
