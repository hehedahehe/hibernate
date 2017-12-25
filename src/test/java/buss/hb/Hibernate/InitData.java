package buss.hb.Hibernate;

import com.hb.entity.Employee;
import com.hb.entity.Grade;
import com.hb.entity.Role;
import com.hb.entity.User;
import org.hibernate.Session;
import org.junit.Test;

/*
 * @desc
 * @author lirb
 * @datetime 2017/12/25,13:40
 */
public class InitData extends HBBaseTest{

    @Test
    public void initData(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = new User("小明");
        user.setGrade(new Grade(10,"数学"));

        User user2 = new User("小红");
        user2.setGrade(new Grade(19,"语文"));

        session.save(user);
        session.save(user2);


        Role role = new Role("销售部经理","负责公司销售");
        Role role2 = new Role("工程部经理","负责公司技术");
        Role role3 = new Role("人事部经理","负责人事相关事宜");


        session.save(role);
        session.flush(); //会将session管理下的实体（瞬时、游离、持久化）同步到数据库
        session.save(role2);
        session.save(role3);


        Employee employee1 = new Employee("张三","客服1号");
        Employee employee2 = new Employee("李四","客服2号");
        session.save(employee1);
        session.save(employee2);


        session.getTransaction().commit();
        session.close();
    }


}
