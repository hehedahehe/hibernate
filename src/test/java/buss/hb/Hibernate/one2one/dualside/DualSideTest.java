package buss.hb.Hibernate.one2one.dualside;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.Grade;
import com.hb.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;

/*
 * @desc 所谓的双端维护关系，其实是维护的是两段关系。
 * @author lirb
 * @datetime 2017/10/23,19:55
 */
public class DualSideTest extends HBBaseTest{
    /***单边维护关系***/

    /**
     * 1. 目前设置user->grade的级联属性为
     * @OneToOne(fetch = FetchType.EAGER)
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     * @JoinColumn(name = "grade_id")
     * 2. grade->user
     * @OneToOne(fetch = FetchType.EAGER)
     * @JoinColumn(name = "user_id")
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     *
     * 3.在user端维护关系情况下，通过设置Cascade属性可以彼此级联保存。（反之，由grade端维护关系也是一样）
     */
    @Test
    public void intiResource(){
        //用户级联保存成绩
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Grade grade = new Grade(95,"信用评分");
        User user = new User("Mike");
        user.setGrade(grade);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    @Test
    public void save2(){
        //成绩级联保存用户，这时由于是由User维护关系，通过grade是无法保存关系的
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Grade grade = new Grade(99,"信用评分");
        User user = (User)session.load(User.class, "4028b0815f492d00015f492d0c5c0000");
//        grade.setUser(user);
        session.save(grade);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void load2(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //这个时候由于在save2中无法保存关系，在加载时
        //由于会调用user.grade_id=grade.id进行join操作，由于grade_id为null，会抛出空指针异常
        User user = (User)session.load(User.class, "4028b0815f490190015f49019b0b0001");
        Grade grade = user.getGrade();
        System.out.println(grade.getDescription());
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void del1(){
        //用户级联删除成绩，则会造成User删除成功，而成绩无法删除的情况
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from com.hb.entity.User";
        Query query = session.createQuery(hql);
        List<User> users = query.list();
        for(User user : users){
            session.delete(user);
        }
        session.getTransaction().commit();
        session.close();
    }


    /**
     * oneToone中双向关联的隐患：
     * 想想一下场景：
     * 1. User user = new User(); Grade grade = new Grade(); user.setGrade(grade); session.save(user);
     *  此时，数据库存在了一条关于user的记录，以及他的grade信息；
     * 2. 此时，Grade grade2 = new Grade(); grade2.setUser(user); session.save(grade2);
     *  此时，user多了一条关于grade2的记录
     */
}
