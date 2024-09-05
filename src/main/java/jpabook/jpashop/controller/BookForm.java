package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    // 상품의 공통 속성
    private Long id; // 상품 수정도 해야되기에 id 값이 필요

    private String name;
    private int price;
    private int stockQuantity;

    // 책과 관련된 속성
    private String author;
    private String isbn;
}
