package buss.hb.Hibernate;

import com.hb.entity.Hobby;
import com.hb.entity.Students;
import com.hb.entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/11,10:43
 */
public class StudentsTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        //创建配置对象
        Configuration config = new Configuration().configure();
        //创建服务注册对象
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).build();
        //创建会话工厂对象
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        //会话对象
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();


        Students s = new Students(1, "张三丰", "男",
                new Date(), "武当山");
        s.getIdimgs().add("dfasfafd.jpg");
        s.getIdimgs().add("cadfadf.jpg");

        /*学科*/
        Set<Subject> subjectSet = new HashSet<>();
        Subject subject1 = new Subject("数学");
        subject1.setSubjectDesc("有趣的数学。");
        subjectSet.add(subject1);
        s.setSubjects(subjectSet);

        /*兴趣*/
        Map<String, Hobby> hobbyMap = new HashMap<>();
        hobbyMap.put("篮球",new Hobby("打篮球"));
        s.setHobbyMap(hobbyMap);

        session.save(s); //保存对象进入数据库
        transaction.commit();
    }

    @After
    public void destory() {
        transaction.commit(); //提交事务
        session.close(); //关闭会话
        sessionFactory.close(); //关闭会话工厂
    }

    @Test
    public void testSaveStudents() {
        //生成学生对象
        Students s = new Students(1, "张三丰", "男",
                new Date(), "武当山");
        Set<Subject> subjectSet = new HashSet<>();
        Subject subject1 = new Subject("数学");
        subject1.setSubjectDesc("有趣的数学。");
        subjectSet.add(subject1);
        Subject subject2 = new Subject("数学");
        subjectSet.add(subject2);
        s.setSubjects(subjectSet);
        session.save(s); //保存对象进入数据库
    }


    @Test
    public void testGetStudents(){
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        int id = 1;
        Students s = (Students) session.get(Students.class,id);
        System.out.println("===========GET===============");
        for(Subject subject : s.getSubjects()){
            System.out.println(s.getSname()+"===>"+subject.getName());
            System.out.println(s.getSname()+"===>"+subject.getSubjectDesc());
        }

        for(String img : s.getIdimgs()){
            System.out.println(s.getSname()+"===>"+img);
        }

        Map<String,Hobby> hobbyMap = s.getHobbyMap();
        for(String h : hobbyMap.keySet()){
            System.out.println(h+"====>"+hobbyMap.get(h));
        }
    }

    @Test
    public void testLoadStudents(){
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        int id = 1;
        Students s = (Students) session.load(Students.class,id);
        System.out.println("=============LOAD=============");
        for(Subject subject : s.getSubjects()){
            System.out.println(s.getSname()+"===>"+subject.getName());
            System.out.println(s.getSname()+"===>"+subject.getSubjectDesc());
        }

        for(String img : s.getIdimgs()){
            System.out.println(s.getSname()+"===>"+img);
        }
    }

//    @Test
//    public void testLoadNotLazyStudents(){
//        session = sessionFactory.openSession();
//        //开启事务
//        transaction = session.beginTransaction();
//        int id = 1;
//        Students s = (Students) session.load(Students.class,id);
//        System.out.println("=============LOAD=NOT=LAZY===========");
//        for(Subject subject : s.getSubjects()){
//            System.out.println("**************");
//            System.out.println(s.getSname()+"===>"+subject.getName());
//        }
//    }


    @Test
    public void getBackPointer(){
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        int id = 1;
        Students s = (Students) session.get(Students.class,id);
        System.out.println("=============BACk=POINTER============");
            for(Subject subject : s.getSubjects()){
            Students ss = subject.getStudents();
            System.out.println("**************");
            System.out.println(ss.getSname()+"===>"+subject.getName());
        }
    }
}
