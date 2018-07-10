package com.xmxc.generator.service ;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.model.OrderItemEntity;

import com.xmxc.generator.generator.InterfaceGenerator;

import java.lang.String;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.xmxc.generator.dao.OrderItemDao;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public OrderItemEntity queryGoods(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest) {
        return orderItemDao.queryGoods(interfaceGenerator, generatorTest);
    }

    public void deleteGoods(String string) {
        orderItemDao.deleteGoods(string);
    }

}