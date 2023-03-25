package ua.factoriald.salestest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Item extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    String name;

    String price;

}
