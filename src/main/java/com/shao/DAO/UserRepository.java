package com.shao.DAO;

import com.shao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shao on 2019/4/2 15:43.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User getPasswordByUsername(String username);
}
