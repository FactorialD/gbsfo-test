package ua.factoriald.salestest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ua.factoriald.salestest.entity.abstraction.AbstractEntity;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderStatus extends AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    String orderName;

    /**
     * TODO add fields attr_begin, attr_end for better status management
     * in cause of need to disable statuses while keeping old records
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderStatus that = (OrderStatus) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
