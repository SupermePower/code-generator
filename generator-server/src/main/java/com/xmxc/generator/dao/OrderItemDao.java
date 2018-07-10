package com.xmxc.generator.dao ;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.generator.InterfaceGenerator;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao {

    OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest);

    void deleteGoods(InterfaceGenerator interfaceGenerator);

}