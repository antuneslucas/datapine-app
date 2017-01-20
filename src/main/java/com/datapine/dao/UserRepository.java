package com.datapine.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.datapine.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByEmail(String email);

    @Override
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    Iterable<User> findAll();

}
