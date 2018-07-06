package com.xmxc.generator.controller;

import com.xmxc.generator.generator.ClassGenerator;
import com.xmxc.generator.generator.EntityGenerator;
import com.xmxc.generator.generator.InterfaceGenerator;
import com.xmxc.generator.util.CreateMethodParam;
import com.xmxc.generator.util.CreateObjectParam;
import com.xmxc.generator.util.ParserXMLHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GeneratorController {

    @Autowired
    private InterfaceGenerator interfaceGenerator;


    public static void main(String[] args) {

        List<CreateMethodParam> createMethodParam = getCreateMethodParam();
        System.out.println(createMethodParam);

        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();
        List<CreateObjectParam> createObjectData = parserXMLHelper.getCreateObjectData();
        System.out.println(createObjectData);


        for (CreateObjectParam createObjectParam : createObjectData) {
            if ("serviceImpl".equals(createObjectParam.getFileType())) {
                //TODO 创建class实现
                String filePath = createObjectParam.getFilePath();
                String fileName = createObjectParam.getFileName();
            } else if ("service".equals(createObjectParam.getFileType())) {
                //TODO 业务接口
            } else if ("dao".equals(createObjectParam.getFileType())) {
                //TODO dao
            }
        }

        //TODO 创建Mapper
//        createInterfaceTest("GoodsMapper", "/Users/apple/work/xmxc/code-generator/generator-server/src/main/java/com/xmxc/generator/mapper", "com.xmxc.generator.mapper");
//        //TODO 创建Service
//        createInterfaceTest("GoodsService", "/Users/apple/work/xmxc/code-generator/generator-server/src/main/java/com/xmxc/generator/service", "com.xmxc.generator.service");
//        //TODO 创建业务实现
//        createClassTest();


        // model创建
        createModelTest();
    }

    /**
     * 创建业务实现类
     */
    private static void createClassTest() {
        /**--------------xml文件中获取-----------------*/
        String filePath = "/Users/apple/work/xmxc/code-generator/generator-server/src/main/java/com/xmxc/generator/service";
        String fileName = "GoodsServiceImpl";
        String returnType = "com.xmxc.generator.model.GoodsEntity";
        String callMapperType = "com.xmxc.generator.service.GoodsMapper";
        String implementsInterface = "GoodsService";
        Map<String, String> methodParam = new HashMap<>();
        methodParam.put("queryGoodsList", "java.lang.String");
        methodParam.put("updateGoods", "com.xmxc.generator.model.GoodsEntity");
        methodParam.put("deleteGoods", "java.lang.Long");
        methodParam.put("addGoods", "com.xmxc.generator.model.GoodsEntity");
        /**-------------------------------------*/
        String filePackageName = "com.xmxc.generator.service";
        ClassGenerator.createClass(filePath, fileName, filePackageName, methodParam, implementsInterface, returnType, callMapperType);
    }

    /**
     * 创建实体
     */
    private static void createModelTest() {
        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();
        Map<String, String> createModelData = parserXMLHelper.getCreateModelData();
        EntityGenerator.createModel(createModelData);
    }

    /**
     * 创建接口
     */
    private static void createInterfaceTest(String interfaceName, String filePath, String filePackage) {
        String returnType = "com.xmxc.generator.model.GoodsEntity";
        Map<String, String> methodParam = new HashMap<>();
        methodParam.put("queryGoodsList", "java.lang.String");
        methodParam.put("updateGoods", "com.xmxc.generator.model.GoodsEntity");
        methodParam.put("deleteGoods", "java.lang.Long");
        methodParam.put("addGoods", "com.xmxc.generator.model.GoodsEntity");
        InterfaceGenerator.createInterface(filePath, interfaceName, filePackage, methodParam, returnType);
    }

    /**
     * 获取创建方法数据
     *
     * @return
     */
    private static List<CreateMethodParam> getCreateMethodParam() {
        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();
        return parserXMLHelper.getCreateMethodsData();
    }
}
