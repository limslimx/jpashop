package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.item.Item;
import com.lim.jpashop.springboot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service //이렇게 Repository를 그저 위임할 뿐인 서비스를 만드는 것은 생각해볼 필요가 있다. Controller에서 바로 Repository로 접근해서 사용할 수도 있고...
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
