package com.datapine.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.datapine.domain.Item;

@PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Override
    @PostFilter ("filterObject.owner == authentication.name or hasRole('ROLE_ADMIN')")
    Iterable<Item> findAll();

    @Override
    @PostFilter ("filterObject.owner == authentication.name or hasRole('ROLE_ADMIN')")
    Item findOne(Long itemId);

}
