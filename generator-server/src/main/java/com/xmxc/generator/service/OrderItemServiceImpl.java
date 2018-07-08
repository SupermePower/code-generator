package com.xmxc.generator.service ;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.controller.GeneratorController;

import com.xmxc.generator.generator.InterfaceGenerator;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.xmxc.generator.dao.OrderItemDao;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorController generatorController) {
        return orderItemDao.queryGoods(interfaceGenerator, generatorController);
    }

    public void deleteGoods(InterfaceGenerator interfaceGenerator) {
        orderItemDao.deleteGoods(interfaceGenerator);
    }

}