package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.Delivery;
import com.lim.jpashop.springboot.domain.Member;
import com.lim.jpashop.springboot.domain.Order;
import com.lim.jpashop.springboot.domain.OrderItem;
import com.lim.jpashop.springboot.domain.item.Item;
import com.lim.jpashop.springboot.repository.ItemRepository;
import com.lim.jpashop.springboot.repository.MemberRepository;
import com.lim.jpashop.springboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long Order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); //개발 시에 한 명은 이런식으로 코드를 작성하고 한 명은 생성자를 이용하여 orderItem을 생성하면 유지보수가 어려울 수 있으므로 생성자를 이용해 생성을 못하도록 protected로 막아준다

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); //이 역시도 orderItem과 같은 이유로 생성자를 protected로 지정해준다

        //주문 저장
        orderRepository.save(order); //deliverRepository 및 orderItemRepository를 통해 따로 delivery나 orderItem을 저장해줄 필요가 없는 이유는 Order엔티티에서 적용한 cascade 옵션 덕분이다

        return order.getId();
    }


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(long orderId){

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel(); //JPA를 사용하면 이렇게 데이터 값만 바꿔줘도 더티체킹(변경감지) 기능을 통해 변한 값을 자동으로 DB에 날려준다는 어마어마한 장점이 존재한다
    }

//    검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }

}
