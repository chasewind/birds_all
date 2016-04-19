package com.bird.spring;

import java.math.BigDecimal;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class CalcParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String operator = element.getAttribute("operator");
        String number1 = element.getAttribute("number1");
        String number2 = element.getAttribute("number2");
        Assert.notNull(operator, "operaotr is required.");
        builder.addPropertyValue("number1", number1);
        builder.addPropertyValue("number2", number2);
        if (StringUtils.hasText(operator)) {
            builder.addPropertyValue("operator", operator);
            if ("add".equalsIgnoreCase(operator)) {
                processAdd(element, builder);
            } else if ("minus".equalsIgnoreCase(operator)) {
                processMinus(element, builder);
            } else {
                throw new UnsupportedOperationException("UnSupport Operaotr");
            }
        }
    }

    private void processMinus(Element element, BeanDefinitionBuilder builder) {
        try {
            
            builder.addPropertyValue("result",
                                     new BigDecimal((String) assertAttributeNotNull(element,
                                                                                    "number1")).subtract(new BigDecimal((String) assertAttributeNotNull(element,
                                                                                                                                                      "number2"))));
        } catch (Exception e) {
            throw new IllegalArgumentException("Number1/Number2 Must be Number.");
        }
    }

    private void processAdd(Element element, BeanDefinitionBuilder builder) {
        try {
            builder.addPropertyValue("result",
                                     new BigDecimal((String) assertAttributeNotNull(element,
                                                                                    "number1")).add(new BigDecimal((String) assertAttributeNotNull(element,
                                                                                                                                                   "number2"))));

        } catch (Exception e) {
            throw new IllegalArgumentException("Number1-Number2 Must be Number.");
        }
    }

    private Object assertAttributeNotNull(Element element, String attributeName) {
        String attribute = element.getAttribute(attributeName);
        return StringUtils.hasText(attribute) ? StringUtils.trimWhitespace(attribute) : "";
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Calculator.class;
    }

}
