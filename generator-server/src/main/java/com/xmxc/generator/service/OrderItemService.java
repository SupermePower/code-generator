package com.xmxc.generator.service ;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.generator.InterfaceGenerator;

import java.lang.String;

public interface OrderItemService {

    OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest);

    void deleteGoods(String string);

}