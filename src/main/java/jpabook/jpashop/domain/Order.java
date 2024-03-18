package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //  @ManyToOne(fetch = FetchType.LAZY)(fetch = FetchType.EAGER)
    //  JPQL select o from order o; -> SQL select * from order 100 + 1(order)
    //  order를 조회할 때, id값과 맞는 모든 member row를 조회해 오기에 만약에 해당 id값의 mmember이 100개면
    //  100개의 row를 전부 조회해오는 현상이 발생할 수 있다. 이는 최적화에 문제가 되니 주의하자
    //  따라서, FetchType.EAGER(즉시로딩)가 아닌 FetchType.LAZY(지연로딩)으로 설정해야 한다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // XToOne은 기본 패치 전략이 EAGER이다.
    // OneToX는 기본 패치 전략이 LAZY이다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    /* 연관관계 편의메서드*/
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
