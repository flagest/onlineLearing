package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wu on 2020/3/12 0012
 */
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser, String> {
    //根据用户id来查询公司账号
    XcCompanyUser findByUserId(String userId);
}
