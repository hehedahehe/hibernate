package buss.hb.Hibernate.many2many;

import buss.hb.Hibernate.HBBaseTest;
import com.hb.entity.MUser;
import org.junit.Test;

import java.util.List;


/*
 * @desc
 * @author lirb
 * @datetime 2017/9/22,11:44
 */
public class QueryTest extends HBBaseTest {

    /**
     * 事物由Hb自己管理
     */
    @Test
    public void testQuery(){
        session = sessionFactory.openSession();
        txHb = session.getTransaction();
        txHb.begin();

        String sql = "from com.hb.entity.many2many.MUser";
        List<MUser> users = session.createQuery(sql).list();
        for(MUser user : users){
            System.out.println(user.getName()+":"+user.getRoles());
        }


        txHb.commit();
        session.close();
    }


}
