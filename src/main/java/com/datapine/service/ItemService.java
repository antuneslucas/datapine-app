package com.datapine.service;

import com.datapine.domain.Item;

public interface ItemService {

    Item save(Item item);

    Item findItem(Long itemId);

    Iterable<Item> findAllItems();

}
