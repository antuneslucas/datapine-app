package com.datapine.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datapine.dao.ItemRepository;
import com.datapine.domain.Item;
import com.datapine.service.AclSecurityService;
import com.datapine.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired private ItemRepository itemRepository;
    @Autowired private AclSecurityService aclSecurityService;

    @Override
    @Transactional
    public Item save(Item item) {
        Item saved = itemRepository.save(item);
        aclSecurityService.setPermission(saved);

        return saved;
    }

    @Override
    public Item findItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    public Iterable<Item> findAllItems() {
        return itemRepository.findAll();
    }

}
