package com.lanou.dao;

import com.lanou.domain.Student;

/**
 * Created by dllo on 17/10/17.
 */
public interface StudentDao extends BaseDao<Student> {

    /**
     * 学生登录查询
     * @param sname 用户名
     * @param pwd 密码
     * @return 成功返回true, 否则返回false
     */
    boolean login(String sname, String pwd);

}
