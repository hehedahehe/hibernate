package buss.hb.Hibernate.many2one;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.Employee;
import com.hb.entity.Grade;
import com.hb.entity.User;
import org.hibernate.Session;
import org.junit.Test;

import java.util.Set;

/*
 * @desc
 * 1. 在ManyToOne中没有mappedby
 * 2. 而在OneToMany端可以指定是否进行关系维护
 * @author lirb
 * @datetime 2017/10/24,10:29
 */
public class ManyToOneTest extends HBBaseTest {

    @Test
    public void initResource(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = new Employee();
        employee.setName("TomEE");
        User user = new User();
        user.setName("Mike");
//        user.setEmployee(employee);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     *在One端维护关系，同时没有进行删除的级联配置，而尝试删除One端实例时，会抛出异常。
     *org.hibernate.ObjectDeletedException: deleted object would be re-saved by cascade (remove deleted object from associations):
     * @OneToMany(fetch = FetchType.LAZY)
     * @JoinColumn(name = "employee_id")
     * @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
     * 在这样的配置情况下，若尝试删除employee，数据库删除删除时没有问题，而在hibernate层面判断，若删除了employee，判断到没有级联删除，这样造成的后果是
     * employee删除了，而在user端还维护着这段关系，这样便造成了数据不完整。
     * One端删除：所以要想从one端时不能够进行单独删除的，要删除则必须一块删除，即配置级联删除项。
     * Many端删除：删除则不会存在这种问题，因为Many删除后，不会造成数据不完整。
     */
    @Test
    public void testLoadFromEmployee(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = (Employee)session.load(Employee.class,"4028b0815f4c542c015f4c54305d0001");
//        Set<User> users = employee.getUsers();
//        for(User user : users){
//            System.out.println(user.getEmployee().getName());
//        }
        session.delete(employee);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * many端是否设置delete级联都行，都可以删除成功，而不像one端会抛出异常。
     * 所以可以根据业务需要进行设置delete属性。
     */
    @Test
    public void deleteFromMany(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User)session.load(User.class,"4028b0815f4c6acb015f4c6acf520000");
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }



}
