package com.shao.Repository;

import com.shao.Domain.Messageboard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shao on 2019/4/13 20:56.
 */
public interface MessageboardRepository extends JpaRepository<Messageboard,Long> {
}
