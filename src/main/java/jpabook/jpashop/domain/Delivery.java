package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Address;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    // ORDINAL 이면 숫자로 1,2,3...으로 들어간다. 중간에 다른 값이 들어가면 숫자가 밀려서 절대 쓰지말자
    // STRING 으로 하면 말그대로 STRING을 읽기때문에 중간에 추가되도 문제가 없다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
