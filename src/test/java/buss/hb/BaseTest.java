package buss.hb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/22,11:44
 */
@ContextConfiguration(locations = { "classpath:beans-buss-test.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {

    /**JPA**/
    @Resource(name="bussEntityManagerFactory")
    public EntityManagerFactory bussEntityManagerFactory;
    public EntityManager em;
    public EntityTransaction tx;


    /**Hibernate**/
    @Resource(name="sessionFactory")
    public SessionFactory sessionFactory;
    public Session session;
    public Transaction txHb;


    @Before
    public void initResource(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();

        //session = sessionFactory.openSession();
        //txHb = session.getTransaction();
    }



}
