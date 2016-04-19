package com.bird.spring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.bird.annotations.config.ServiceConfig;
import com.bird.annotations.interf.Party;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes={ServiceConfig.class })
public class SpringWiringTest {
    //// 以下三种表达方式全部报错
    // @Resource
    // private Party party;

    // @Autowired
    // private Party party;

    // @Inject
    // private Party party;
    /*** ------------------------------ */

     @Resource(name="person")
     private Party person;
//    @Autowired
//    private Party person;

    // @Inject
    // private Party person;
    @Test
    public void test() {
        System.out.println("go...");
    }
}
