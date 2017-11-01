package buss.hb.Hibernate.many2one;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.Employee;
import com.hb.entity.User;
import org.hibernate.Session;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/26,14:32
 */
public class OneToManyTest extends HBBaseTest {

    @Test
    public void initResource(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = new Employee();
        employee.setName("TomEE");
        User user = new User();
        user.setName("Mike");
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        employee.setUsers(userSet);
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }
}
