package com.bird.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SimpleCalc extends NamespaceHandlerSupport{

    @Override
    public void init() {
        registerBeanDefinitionParser("data", new CalcParser());
    }

}
