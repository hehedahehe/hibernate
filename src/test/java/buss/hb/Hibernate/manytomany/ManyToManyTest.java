package buss.hb.Hibernate.manytomany;

import com.hb.entity.Role;
import com.hb.entity.User;
import org.junit.Test;

/*
 * @desc 测试ManyToMany
 * @author lirb
 * @datetime 2017/12/25,12:01
 */
public class ManyToManyTest {
    /**
     * 测试级联保存
     */
    @Test
    public void testCascadeSave(){
        User user = new User("小明");
    }

    /**
     * 测试多对多情况下的级联删除
     */
    @Test
    public void testCascadeDelete(){

    }
}


