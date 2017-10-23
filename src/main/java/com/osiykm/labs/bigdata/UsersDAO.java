package com.osiykm.labs.bigdata;

import org.springframework.data.repository.PagingAndSortingRepository;

/***
 * @author osiykm
 * created 22.10.2017 23:31
 */
public interface UsersDAO extends PagingAndSortingRepository<User, Long> {
}
