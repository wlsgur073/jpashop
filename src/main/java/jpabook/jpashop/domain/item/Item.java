package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
// JOINED 가장 정교화된 스타일, SINGLE_TABLE 한 테이블로 만듬, TABLE_PER_CLASS 상속 받은 클래스마다 테이블 생성
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity; // 제고

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    /* 비즈니스 로직 */
    // 엔티티 안에서 해결할 수 있는 부분은 엔티티 클래스에서 비즈니스 로직을 수행한다.
    // setter를 사용하는 것이 아니라 엔티티 안에 비즈니스 로직을 가지고 set을 수행하는 것이 좋다.

    /**
     * stock 증가
     * */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
