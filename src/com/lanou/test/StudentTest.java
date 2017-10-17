package com.lanou.test;

import com.lanou.domain.Student;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * 针对Student的单元测试
 * Created by dllo on 17/10/17.
 */
public class StudentTest {

    /* session的工厂类对象, 是线程安全的, 一个数据库对应一个session工厂类对象
     * 通常在项目启动时间初始化该对象, 用Configuration对象创建 */
    private SessionFactory sessionFactory;

    /* 真正执行CRUD操作的数据库连接对象, 代表了一次数据库连接, 是非线程安全的 */
    private Session session;

    /* 事务对象, 与jdbc中的事务类似, 只不过hibernate中的事务不会自动提交的,
    而jdbc中的事务是会自动提交的,hibernate事务需要手动提交 */
    private Transaction transaction;

    /* 初始化操作
    * Before : 在 Test 注解之前执行的方法, 通常该方法中做一些初始化操作*/
    @Before
    public void init(){

        System.out.println("路过 -> init");

        // 创建 hibernate 配置对象
        Configuration configuration = new Configuration();

        // 加载指定配置文件
        configuration.configure("hibernate.cfg.xml");

        // 通过配置对象创建一个sessionFactory对象,创建结束之后configuration就与sessionFactory失去关联
        sessionFactory = configuration.buildSessionFactory();

        // 打开一个数据库连接
        session = sessionFactory.openSession();

        // 开启事务
        transaction = session.beginTransaction();

    }

    /* 销毁操作
    * After : 在Test注释之后执行的方法, 通常该方法中做一些释放, 关闭的操作*/
    @After
    public void destroy(){

        System.out.println("路过 -> destroy");

        //提交本次事务
        transaction.commit();

        //关闭本次连接
        session.close();

    }

    /* 数据库插入
    * Test : 单元测试执行方法*/
    @Test
    public void insert(){

        System.out.println("路过 -> insert");

        Student student = new Student("张三","男", 23);

        //将实体类对象保存到数据库中
        session.save(student);

    }

    @Test
    public void query(){
        //获得一个查询对象, 等同于 select * from student
        Query query = session.createQuery("from Student where sname=?");

        //条件语句中的占位符参数对应
        query.setString(0,"张三"); //指定第一个 ? 所对应的值

        //返回查询的结果集
        List<Student> students = query.list();

        //遍历结果集
        for (Student student : students) {

            System.out.println(student);

        }

    }

    @Test
    public void delete(){
        //从数据库中查询到要删除的对象
        Query query = session.createQuery("from Student where sname=?");

        query.setString(0,"张三");

        List<Student> students = query.list();

        if(students.size() > 0){
            //如果能查询到'张三'这个用户 则进行删除第一个叫张三的用户
            session.delete(students.get(0));

        }

    }

    @Test
    public void update(){

        Query query = session.createQuery("from Student where sname=?");

        query.setString(0,"张三");

        List<Student> students = query.list();

        if(students.size() > 0){

            /* 取第一个叫张三的学生 */
            Student student = students.get(0);

            /* 修改该学生的基础信息 */
            student.setSname("李四");
            student.setGender("女");
            student.setAge(32);

            session.update(student);

        }

    }

    @Test
    public void createCriteria(){

        //获得要进行查询的student对应criteria接口对象 它比HQL更加高级的查询方法

        Criteria criteria = session.createCriteria(Student.class);
        //设置多条查询条数
        criteria.setMaxResults(2);
        //获取结果集
        List<Student> students = criteria.list();

        for (Student stu : students) {

            System.out.println(stu);

        }

    }

    @Test
    public void querySingle(){

        Query query = session.createQuery("from Student");

        //返回单个结果
        Student student = (Student) query.uniqueResult();

        System.out.println(student);

    }

}
