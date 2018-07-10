package com.xmxc.generator.dao ;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.generator.InterfaceGenerator;

import java.lang.String;

import com.xmxc.generator.model.FoodStoreEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface FoodStoreDao {

    FoodStoreEntity queryFoodStore(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest);

    void deleteFoodStore(String string);

}