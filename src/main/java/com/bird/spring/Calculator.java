package com.bird.spring;
/**
 * 
 * 类Calculator.java的实现描述：TODO 类实现描述
 * @author dongwei.ydw 2016年3月15日 上午11:14:17
 */
public class Calculator {

    // <xsd:attribute name="number1" type="xsd:decimal" />
    // <xsd:attribute name="number2" type="xsd:decimal" />
    // <xsd:attribute name="operator" type="xsd:string" />

    private double number1;
    private double number2;
    private String operator;
    private double result;

    public double getNumber1() {
        return number1;
    }

    public void setNumber1(double number1) {
        this.number1 = number1;
    }

    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    
    public double getResult() {
        return result;
    }

    
    public void setResult(double result) {
        this.result = result;
    }

}
