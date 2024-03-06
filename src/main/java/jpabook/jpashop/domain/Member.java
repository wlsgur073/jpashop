package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입이라는 걸 명시해줌
    private Address address;

    @OneToMany(mappedBy = "member") // order  클래스에 있는 meber 필드에 매핑하겠다는 의미
    private List<Order> orders = new ArrayList<>();
}
