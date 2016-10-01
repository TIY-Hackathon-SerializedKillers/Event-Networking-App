package com.tiy;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 9/29/16.
 */
public interface FriendRepository extends CrudRepository<Friend, Integer> {
    public Iterable<Friend> findAllByUserId(int userId);
}
