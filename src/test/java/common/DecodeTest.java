package common;

import org.junit.Test;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/17,16:34
 */
public class DecodeTest {
    @Test
    public void testDecode() throws Exception{
        String a = "我";
        byte[] bytes2 = a.getBytes("GB2312");
        for(byte b : bytes2){
            System.out.println(b);
        }
    }
}
