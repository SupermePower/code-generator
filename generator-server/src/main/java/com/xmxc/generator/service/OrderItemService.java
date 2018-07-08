package com.xmxc.generator.service ;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.controller.GeneratorController;

import com.xmxc.generator.generator.InterfaceGenerator;

public interface OrderItemService {

    OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorController generatorController);

    void deleteGoods(InterfaceGenerator interfaceGenerator);

}