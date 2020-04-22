package com.lim.jpashop.springboot.service;

import com.lim.jpashop.springboot.domain.item.Book;
import com.lim.jpashop.springboot.domain.item.Item;
import com.lim.jpashop.springboot.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 상품_등록() throws Exception{
        //given
        Book book=new Book();
        book.setName("book");

        //when
        itemService.saveItem(book);

        //then
        em.flush();
        assertEquals(book, itemRepository.findOne(book.getId()));
    }

}