package buss.hb.JPA.many2many;

import buss.hb.BaseTest;
import com.hb.entity.many2many.MRole;
import com.hb.entity.many2many.MUser;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/22,11:44
 */
public class QueryTest extends BaseTest {

    @Test
    public void testQuery(){
        String sql = "select user from MUser user where user.name like '%红%'";
        Query query = em.createQuery(sql);
        MUser  user = (MUser)query.getSingleResult();
        Assert.assertNotNull(user);
        System.out.println("姓名："+ user.getName());
        for(MRole role : user.getRoles()){
            System.out.println("角色：==>" + role.getName());
            for(MUser user1 : role.getUsers()){
                System.out.println("   用户：==>"  + user1.getName());
            }
        }

    }


}
