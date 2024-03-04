package jpabook.jpashop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
