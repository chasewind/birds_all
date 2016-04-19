package com.bird;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IntrospectorDemo {

    private static Logger logger= LoggerFactory.getLogger(IntrospectorDemo.class);
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        setProperty(person, "name", "张三");
        logger.info(person.getName());
        setProperty(person, "birthDay", new Date());
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(person.getBirthDay()));

        setProperty(person, "sex", Person.SexEnum.female);
        logger.info(person.getSex()+"");
    }

    public static Object getProperty(Object obj, String propertyName) throws IntrospectionException,
                                                                      IllegalAccessException, IllegalArgumentException,
                                                                      InvocationTargetException {

        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method method = pd.getReadMethod();
        return method.invoke(obj);

    }

    public static void setProperty(Object obj, String propertyName,
                                   Object value) throws IntrospectionException, IllegalAccessException,
                                                 IllegalArgumentException, InvocationTargetException {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method method = pd.getWriteMethod();
        logger.info(method == null?"不存在写方法":"存在"+method.getName());
        method.invoke(obj, value);
    }
}
