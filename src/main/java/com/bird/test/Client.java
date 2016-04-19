package com.bird.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bird.spring.Calculator;

public class Client {
public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    Calculator ca=(Calculator)ctx.getBean("cal");
    System.out.println(ca.getNumber1()+"\t"+ca.getOperator()+"\t"+ca.getNumber2()+"=\t"+ca.getResult());
}
}
