package com.xmxc.generator.service ;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.generator.InterfaceGenerator;

public interface OrderItemService {

    OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest);

    void deleteGoods(InterfaceGenerator interfaceGenerator);

}