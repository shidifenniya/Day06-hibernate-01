package com.lanou.test;

import com.lanou.dao.StudentDao;
import com.lanou.dao.impl.StudentDaoImpl;
import com.lanou.domain.Student;
import org.junit.Test;

/**
 * Created by dllo on 17/10/17.
 */
public class StudentDaoTest {

    @Test
    public void save(){
        // 创建一个dao层的对象
        StudentDao studentDao = new StudentDaoImpl();

        Student student = new Student("佳硕","男",24);

        System.out.println("保存前 -->>" + student);

        studentDao.save(student);//保存对象

        System.out.println("保存后 -->>" + student);

    }

    @Test
    public void login(){

        StudentDao studentDao = new StudentDaoImpl();

        boolean b = studentDao.login("栾斌", "123");

        System.out.println("返回结果:" + b);

    }
}
