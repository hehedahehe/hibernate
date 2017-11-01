package common;

import com.hb.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/11,12:02
 */
public class SetTest {

    @Test
    public void testSetAdd(){
        User user1 = new User();
        user1.setName("xiaoming");
        user1.setId("1");
        user1.setDescription("我是xiaoming");
        User user2 = new User();
        user2.setName("xiaoming");
        user2.setId("1");
        user2.setDescription("我也是xiaoming");

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        for(User user :users){
            System.out.println(user.getDescription());
        }

    }
}
