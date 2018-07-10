package com.xmxc.generator.service ;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.generator.InterfaceGenerator;

import java.lang.String;

import com.xmxc.generator.model.FoodStoreEntity;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.xmxc.generator.dao.FoodStoreDao;

@Service
public class FoodStoreServiceImpl implements FoodStoreService {

    @Autowired
    private FoodStoreDao foodStoreDao;

    public FoodStoreEntity queryFoodStore(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest) {
        return foodStoreDao.queryFoodStore(interfaceGenerator, generatorTest);
    }

    public void deleteFoodStore(String string) {
        foodStoreDao.deleteFoodStore(string);
    }

}