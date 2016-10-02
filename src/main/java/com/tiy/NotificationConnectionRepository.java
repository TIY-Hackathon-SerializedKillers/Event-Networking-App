package com.tiy;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 10/2/16.
 */
public interface NotificationConnectionRepository extends CrudRepository<NotificationConnection, Integer> {
    public Iterable<NotificationConnection> findAllByUserId(int userId);
    public NotificationConnection findByUserIdAndFriendId(int userId, int friendId);
}
