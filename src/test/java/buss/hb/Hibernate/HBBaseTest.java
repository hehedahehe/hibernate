package buss.hb.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/26,21:36
 */
@ContextConfiguration(locations = { "classpath:beans-buss-test.xml" })
public class HBBaseTest  extends AbstractJUnit4SpringContextTests {

    /**Hibernate**/
    @Resource(name="sessionFactory")
    public SessionFactory sessionFactory;


    public Session session;
    public Transaction txHb;

}
