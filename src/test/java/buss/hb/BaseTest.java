package buss.hb;

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

    @Resource(name="bussEntityManagerFactory")
    public EntityManagerFactory bussEntityManagerFactory;

    EntityManager em = bussEntityManagerFactory.createEntityManager();
    EntityTransaction tx = em.getTransaction();


}
