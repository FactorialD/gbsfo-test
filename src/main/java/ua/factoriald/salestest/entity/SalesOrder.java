package ua.factoriald.salestest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ua.factoriald.salestest.entity.abstraction.AbstractEntity;

import java.util.List;
import java.util.Objects;

/**
 * Entity has name 'SalesOrder', not 'Order' due to SQL table generation code
 */
@Entity
@Table(name="sales_order")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SalesOrder extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long number;
    @ManyToOne
    private OrderStatus status;

    //TODO change to ManyToMany if needed
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> totalItems;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> totalPayments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SalesOrder salesOrder = (SalesOrder) o;
        return id != null && Objects.equals(id, salesOrder.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

