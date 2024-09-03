package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    /* 생성 메서드 */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    /* 비즈니스 로직 */
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel(); // order 한 번 주문할 때, 고객이 2개의 상품을 주문할 수도 있으니 각각 삭제해야 함.
        }
    }

    /* 조회 로직 */
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice() {
        /*int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); // orderItems에 주문 가격과 수량이 있기에 둘을 곱해줘야 함.
        }*/
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }







}
