package com.bird.tx;

public interface TxCallableEvent<T> {

    T execute() throws RuntimeException;
}
