package com.hb.utils.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/26,22:25
 */
public class MainTest {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans-buss.xml");
        context.start();
    }}
