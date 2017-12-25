package buss.hb.Hibernate;

import com.hb.entity.Grade;
import com.hb.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;

/*
 * @desc 进行mappedby测试
 *
 *
 * @author lirb
 * @datetime 2017/12/25,14:18
 */
public class OnoToOneTest extends HBBaseTest{

    /**
     * mappedBy属性存在于OneToOne、OneToMany、ManyToMany，而在ManyToOne中则是不存在的
     */

    /**
     * 这是文档的解释。
     *  (Optional) The field that owns the relationship. This
     * element is only specified on the inverse (non-owning)
     * side of the association.
     *
     * User和Grade一对一，并在User端维护关系
     * 1. Grade端设置User字段，并设置mappedBy = "grade"
     *  1.1 可以通过Grade获取用户信息
     * 2. Grade端不设置User字段
     *
     *
     * 注：1和2在数据库层面是相同的，即在User表中维护一个Grade的id，即Grade并不知
     * 到该关系的存在。
     */
    @Test
    public void otoMappedByTest1(){
        //1. 查询所有成绩
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from com.hb.entity.Grade";
        Query query = session.createQuery(hql);
        List<Grade> grades = query.list();
        grades.stream().forEach( g -> {
            System.out.println(g.getUser().getName()+":"+g.getDescription()+":"+g.getScore());
        });
    }



    /**
     * 在otoMappedByTest1的基础上，在不维护关系端测试下级联UPDATE
     * 1. Grade端的级联属性设置即 更新和删除
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     */
    @Test
    public void otoMappedByTest2(){
        //1. 查询所有成绩
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from com.hb.entity.Grade";
        Query query = session.createQuery(hql);
        List<Grade> grades = query.list();
        //测试UPDATE, 此时虽然Grade端没有维护关系，但是是可以进行UPDATE
        grades.stream().forEach( g -> {
            User user = g.getUser();
            user.setDescription(user.getDescription()+"1");
            g.setUser(user);
            session.update(g);
        });
        session.getTransaction().commit();
        session.close();

    }


    /**
     * 在otoMappedByTest2的基础上，在不维护关系端测试下级联SAVE
     * 1. Grade端的级联属性设置即 更新和删除
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     */
    @Test
    public void otoMappedByTest21(){

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //测试SAVE
        User user = new User("小强");
        Grade grade = new Grade(78,"物理");
        grade.setUser(user);

        session.save(grade);

        session.getTransaction().commit();
        session.close();
        /**
         * SQL
         *  insert into tb_grade (description, score, grade_version, id) values (?, ?, ?, ?)
         *  insert into tb_user (description, grade_id, name, id) values (?, ?, ?, ?)
         *  观察数据库，发现User和Grade是分别保存的，但是并未保存他们之间的关联关系
         *  可见：
         *  可以级联保存。
         *  但是不会“保存彼此的关系”，或者说“创建彼此的关系”，因为“我”并不维护关系。
         *  官方用语“拥有关系”是指是否能够“创建彼此的关系”。
         *  一旦关系建立，可以级联删除、更新
         *  关系建立不起来，级联删除、更新也就无从谈起
         *
         *  比如：
         *  1. 先通过otoMappedByTest21进行保存，这个时候级联保存生效，但是Grade无法创立关系
         *  2. otoMappedByTest2中是无法通过Grade获取User的，就会抛出空指针异常
         */
    }


    /**
     * 在otoMappedByTest21的基础上，在不维护关系端测试下级联DELETE
     * 1. Grade端的级联属性设置即 更新和删除
     * @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
     */
    @Test
    public void otoMappedByTest22(){

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //测试DELETE
        String hql = "from com.hb.entity.Grade";
        Query query = session.createQuery(hql);
        List<Grade> grades = query.list();
        //删除所有的成绩
        grades.stream().forEach( g -> {
           session.delete(g);
        });

        session.getTransaction().commit();
        session.close();

        /**
         Hibernate: delete from tb_user where id=?
         Hibernate: delete from tb_grade where id=? and grade_version=?
         Hibernate: delete from tb_user where id=?
         Hibernate: delete from tb_grade where id=? and grade_version=?
         Hibernate: delete from tb_grade where id=? and grade_version=?
         产看数据库，发现确实全删除了
         */
    }

    /**
     * 若是双反都维护关系呢
     * //    @OneToOne(fetch = FetchType.EAGER,mappedBy = "grade")
     @OneToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "user_id")
     *
     */
    @Test
    public void otoDualSide(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //测试SAVE
        User user = new User("小强");
        Grade grade = new Grade(78,"物理");
        grade.setUser(user);

        session.save(grade);

        session.getTransaction().commit();
        session.close();
        /**
         * Hibernate: insert into tb_user (description, grade_id, name, id) values (?, ?, ?, ?)
         Hibernate: insert into tb_grade (description, score, user_id, grade_version, id) values (?, ?, ?, ?, ?)
         可以发现，Grade多了一个UserID字段，并成功保存了Userid字段。但是User端并未保存Grade的任何信息，因为关系是Grade单方面
         发起的，User端并不知情。
         结合otoMappedByTest21可以看出，
         在OneToOne的情况下，只有关系维护方才可以成功保存级联对象。
         */
    }


    @Test
    public void otoDualSide2(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //测试SAVE
        User user = new User("小强");
        Grade grade = new Grade(78,"物理");
        grade.setUser(user);

        session.save(grade);

        user.setGrade(grade);
        session.save(user);

        session.getTransaction().commit();
        session.close();
        /**
         * Hibernate: insert into tb_user (description, grade_id, name, id) values (?, ?, ?, ?)
         Hibernate: insert into tb_grade (description, score, user_id, grade_version, id) values (?, ?, ?, ?, ?)
         可以发现，Grade多了一个user_id字段，并成功保存了user_id字段。
         可以发现，User多了一个grade_id字段，并成功保存了grade_id字段。

         */
    }


    /**
     * 删除用户和成绩
     */
    @Test
    public void deleteUserAndGrade(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql1 = "delete from com.hb.entity.Grade";
        String hql2 = "delete from com.hb.entity.User";

        Query query1 = session.createQuery(hql1);
        Query query2 = session.createQuery(hql2);

        query1.executeUpdate();
        query2.executeUpdate();

        session.getTransaction().commit();
        session.close();

        /**
         * Hibernate: delete from tb_grade
         //多对多的情况下，会先删除彼此的关系，而不会删除实体
         Hibernate: delete from user_role where (user_id) in (select id from tb_user)
         Hibernate: delete from tb_user
         */

    }


    /**
     * 综上：
     * 1. mappedBy与JoinColumn是对立的，意味着是否“维护关系”
     * 2. “维护关系”从技术上讲，指的是一种能力：是否能够创立关系
     * 3. “关系”与级联之间的关系
     * 3.1 级联添加、级联删除、级联更新
     * 若关系没有建立起来：
         * 3.2 不影响级联SAVE，即不管是否是关系维护方（参照2），SAVE的级联效果都是可达的，但是不会建立关系（外键）
         * 3.3 由于级联更新，删除必须建立在“关系”的基础上（外键）->所以，自然是无法更新和删除的
     * 若关系建立起来：
     * 1. 感觉最佳实践是，数据初始化时，首先通过关系维护端进行“建立关系”
     * 2. 然后，级联属性就可以畅通无阻了
     */



}
