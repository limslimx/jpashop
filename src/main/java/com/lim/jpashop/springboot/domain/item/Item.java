package com.lim.jpashop.springboot.domain.item;

import com.lim.jpashop.springboot.domain.Category;
import com.lim.jpashop.springboot.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<>();

    //==비즈니스 로직==//  --> 객체 지향적으로 생각하면 Service에 따라 아래같은 로직들을 넣는 것보다 데이터가 있는 엔티티 클래스에 이러한 로직들을 추가하는 것이 훨씬 응집력이 높다(이렇게 setter를 대신한다)

    /**
     * 재고 증가
     */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
