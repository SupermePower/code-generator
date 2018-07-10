package com.xmxc.generator.service ;

import com.xmxc.generator.test.GeneratorTest;

import com.xmxc.generator.generator.InterfaceGenerator;

import java.lang.String;

import com.xmxc.generator.model.FoodStoreEntity;

public interface FoodStoreService {

    FoodStoreEntity queryFoodStore(InterfaceGenerator interfaceGenerator, GeneratorTest generatorTest);

    void deleteFoodStore(String string);

}