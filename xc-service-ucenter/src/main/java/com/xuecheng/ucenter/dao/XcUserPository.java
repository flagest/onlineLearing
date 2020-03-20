package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wu on 2020/3/12 0012
 */
public interface XcUserPository extends JpaRepository<XcUser, String> {
    //根据账号来查询用户信息
    XcUser findByUsername(String username);

}
