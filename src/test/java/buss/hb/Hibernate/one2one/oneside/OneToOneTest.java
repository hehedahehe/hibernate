package buss.hb.Hibernate.one2one.oneside;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.Grade;
import com.hb.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;

/*
 * @desc 1. 维护关系意味着什么？
 *  1.1 什么是关系
 *      1.1 在hibernate层，维护关系意味着使用mappedBy指定对方维护关系
 *      1.2 在数据库层面
 *         //1.2.1 oneToOne和ManyToOne/OneToMany表结构与是否使用mappedBy无关
 *         1.2.1 是指谁能感知到外键*_id列的存在
 *      1.3 比如，A与B一对一，B通过hibernate层面的mappyedBy指定A来维护关系，那么在进行对象操作时
 *          1.3.1 通过A进行操作时，会使用b_id的相关操作，而通过B级联A时是不会涉及b_id的，
 *             也就是说，通过B保存A，可以级联保存A，但是并不会保存b_id，也是说B并不维护这段关系了
 *          1.3.2 相反，通过A保存B时，则是不仅可以级联保存对象，而且可以保存他们之间的关系，b_id。
 * @author lirb
 * @datetime 2017/10/23,17:40
 */
public class OneToOneTest extends HBBaseTest {
    /***单边维护关系***/

    /**
     * 1. 目前设置user->grade的级联属性为
     * @OneToOne(fetch = FetchType.EAGER)
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     * @JoinColumn(name = "grade_id")
     * 2. grade->user
     * @OneToOne(fetch = FetchType.EAGER, mappedBy = "grade") //user端维护该关系
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
        User user = new User("Mike");
//        grade.setUser(user);
        session.save(grade);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void load2(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //在save2的错误操作下
        //这个时候由于在save2中无法保存关系，在加载时
        //由于会调用user.grade_id=grade.id进行join操作，由于grade_id为null，会抛出空指针异常
        User user = (User)session.load(User.class, "4028b0815f490190015f49019b0b0001");
        Grade grade = user.getGrade();
        System.out.println(grade.getDescription());
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void del2(){
        //在save2的错误操作下
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

    @Test
    public void del3(){
        //在intiResource正确操作下，可以正确删除操作
        //成绩级联删除用户
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from com.hb.entity.Grade";
        Query query = session.createQuery(hql);
        List<Grade> grades = query.list();
        for(Grade grade : grades){
            session.delete(grade);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void save3(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User)session.load(User.class,"4028b0815f493439015f493445a80000");
        Grade grade = new Grade(96,"体育评分");
        user.setGrade(grade);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }


    /**
     * 1. 综上所述：
     *  1.1 保存：关系维护端进行保存操作，这样可以使得关系得以保存
     *  1.2 在1.1正确操作下，删除：关系维护两端都是可以尽心的，查询更新什么的也就不用说了
     *  1.3 总之，保存必须从关系维护端开始的，关系保存后，其他的操作也没有什么问题的。
     */
}
