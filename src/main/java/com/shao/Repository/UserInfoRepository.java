package com.shao.Repository;

import com.shao.Domain.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shao on 2019/4/12 17:10.
 */
public interface UserInfoRepository extends JpaRepository<Userinfo,Long> {

    Userinfo findByUserId(long id);
}
