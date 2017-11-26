package buss.hb.Hibernate.lockTest;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.Grade;
import com.hb.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/*
 * @desc
 * @author lirb
 * @datetime 2017/11/1,16:29
 */
public class LockTest extends HBBaseTest{
    /**
     * 初始化成绩，版本号为0
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

    private Random random = new Random();

    @Test
    public void testUpdateGrade(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from ";
        hql += "User u where u.name like ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,"%"+"Mike"+"%");
        List<User> userList = query.list();
        User mike = userList.get(0);
        Grade grade = mike.getGrade();
        grade.setScore(random.nextInt(100));
        session.saveOrUpdate(mike);
        session.getTransaction().commit();
        session.close();
        System.out.println("");
    }

    @Test
    public void testUpdateGrade2(){
        try{
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String hql = "from ";
            hql += "User u where u.name like ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,"%"+"Mike"+"%");
            List<User> userList = query.list();
            User mike = userList.get(0);
            Grade grade = mike.getGrade();
            grade.setScore(random.nextInt(200));
            session.saveOrUpdate(mike);
            session.getTransaction().commit();
            session.close();
        }catch (StaleObjectStateException e){
            System.out.println("已经有人在您之前更新过该条数据，您若要继续更新，请再次进行更新操作。");
        }
        System.out.println("");

    }


    @Test
    public void testVersion1(){
        String id ="4028b0815f76b7e9015f76b7f0400001";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from ";
        hql += "Grade where id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0,id);
        Grade grade = (Grade) query.list().get(0);
        session.getTransaction().commit();
        session.close();

        grade.setScore(random.nextInt(200)); //游离态

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(grade);

        session.getTransaction().commit();
        session.close();

    }


    @Test
    public void testVersion2(){
        String id ="4028b0815f76b7e9015f76b7f0400001";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from ";
        hql += "Grade where id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0,id);
        Grade grade = (Grade) query.list().get(0);
        grade.setScore(random.nextInt(200));
        session.update(grade);

        session.getTransaction().commit();
        session.close();

    }



}
