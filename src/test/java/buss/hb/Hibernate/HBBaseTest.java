package buss.hb.Hibernate;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/23,17:37
 */
@ContextConfiguration(locations = { "classpath:beans-buss-test.xml" })
public class HBBaseTest  extends AbstractJUnit4SpringContextTests{
    @Resource(name="sessionFactory")
    public SessionFactory sessionFactory;

}
