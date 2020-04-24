package com.lim.jpashop.springboot.repository;

import com.lim.jpashop.springboot.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; //회원이름
    private OrderStatus orderStatus; //주문상태 [ORDER, CANCEl]
}
