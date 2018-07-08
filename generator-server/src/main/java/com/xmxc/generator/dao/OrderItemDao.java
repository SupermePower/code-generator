package com.xmxc.generator.dao ;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.controller.GeneratorController;

import com.xmxc.generator.generator.InterfaceGenerator;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao {

    OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorController generatorController);

    void deleteGoods(InterfaceGenerator interfaceGenerator);

}