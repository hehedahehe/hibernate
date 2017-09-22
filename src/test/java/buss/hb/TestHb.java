package buss.hb;

import com.hb.entity.MRole;
import com.hb.entity.MUser;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/19,18:00
 */
public class TestHb extends BaseTest {

    @Test
    public void addData(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        MRole role2 = em.getReference(MRole.class, 1);
        MRole role1 = em.getReference(MRole.class, 2);


        Set<MRole> roles = new HashSet<>();
        roles.add(role2);
        roles.add(role1);

        MUser user = new MUser("小名");
        user.setRoles(roles);

        em.persist(user);


        tx.commit();
        em.close();
    }


    @Test
    public void testAdd(){
        //测试级联保存，保存用户时，保存其所有的角色
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        MRole role2 = new MRole("教职工");


        Set<MRole> roles = new HashSet<>();
        roles.add(role2);

        MUser user = new MUser("小红");
        user.setRoles(roles);

        em.persist(user);

        tx.commit();
        em.close();

    }


    @Test
    public void testAdd2(){
        //测试级联保存，保存用户时，保存其所有的角色
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        /*设置级联实体属性时，需先查看级联的实体在数据库中是否存在
        * 实例化对象。
        * ** 先查询出持久化对象，再做操作。
        */
        MRole role2 = em.getReference(MRole.class,1);


        Set<MRole> roles = new HashSet<>();
        roles.add(role2);

        MUser user = new MUser("小红");
        user.setRoles(roles);

        em.persist(user);

        tx.commit();
        em.close();
    }

    /****增删改查都应该是在**维护关系**端进行的****/
    /****哪一段应该进行关系的维护，这是由业务来决定的。****/

    @Test
    public void testDel(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        //hibernate 自动：先删除关联关系，然后删除实体
        MUser user = em.getReference(MUser.class, 1);
        em.remove(user);

        tx.commit();
        em.close();
    }

    /**
     * 删除角色
     */
    @Test
    public void testDel2(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        /*若是删除角色呢，在删除角色的时候，由于角色并未**维护关系**，
        即，角色表不知道存在其他的关联，所以，Hibernate就会直接进行删除操作（并未考虑关系的存在）
        结果，到了Mysql数据库中，查看到存在了关系的存在，就抛出foreign key的异常。
        该异常的抛出，是由于开发人员的错误操作导致的。若要删除操作成功，则应该在Role端也维护关系，
        这样Hibernate在删除操作的时候，就知道Role端维护有这个关联关系。在发出SQL语句时，就会自动
        先删除关联关系，然后再删除Role实体。过程就和User的删除操作相同了。
        */
        MRole role = em.getReference(MRole.class, 2);
        em.remove(role);

        tx.commit();
        em.close();
    }


    /**
     * 解除某种关系常用操作
     */
    @Test
    public void testRemoveRelation(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        MUser user = em.getReference(MUser.class, 6);
        MRole role = em.getReference(MRole.class, 1);
        Set<MRole> roles = user.getRoles();
        roles.remove(role);

        em.merge(user);//更新User

        tx.commit();
        em.close();
    }


    /**更新操作**/
    @Test
    public void testUpdate(){
        em = bussEntityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        MUser user = em.getReference(MUser.class, 6);
        user.setName("小红1");

        em.merge(user);//更新User

        tx.commit();
        em.close();
    }
}
