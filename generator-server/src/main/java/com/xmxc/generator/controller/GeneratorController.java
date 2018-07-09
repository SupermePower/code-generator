package com.xmxc.generator.controller;

import com.xmxc.generator.generator.ClassGenerator;
import com.xmxc.generator.generator.EntityGenerator;
import com.xmxc.generator.generator.InterfaceGenerator;
import com.xmxc.generator.util.CreateMethodParam;
import com.xmxc.generator.util.CreateObjectParam;
import com.xmxc.generator.util.ParserXMLHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class GeneratorController {

    @Autowired
    private InterfaceGenerator interfaceGenerator;


    public static void main(String[] args) {

        System.out.println("long".equalsIgnoreCase("Long"));
//        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();

        //TODO 创建Mapper
//        createInterface(parserXMLHelper);
        //TODO 创建业务实现
//        createClassTest();


        // model创建
        //createModelTest();
    }

    /**
     * 创建dao与service
     *
     * @param parserXMLHelper
     */
    private static void createInterface(ParserXMLHelper parserXMLHelper) {
        List<CreateObjectParam> createObjectData = parserXMLHelper.getCreateObjectData();
        for (CreateObjectParam createObjectParam : createObjectData) {
            if (!"serviceImpl".equals(createObjectParam.getFileType())) {
                String interfaceName = createObjectParam.getFileName();
                String filePath = createObjectParam.getFilePath();
                String filePackage = createObjectParam.getPackageName();
                String fileType = createObjectParam.getFileType();
                createInterfaceTest(interfaceName, filePath, filePackage, fileType);
            }
        }
    }

    /**
     * 创建业务实现类
     */
    private static void createClassTest() {
        String filePath = "";
        String fileName = "";
        String callMapperType = "";
        String callMapperPackage = "";
        String implementsInterface = "";
        String filePackageName = "com.xmxc.generator.service";
        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();
        List<CreateObjectParam> createObjectData = parserXMLHelper.getCreateObjectData();
        for (CreateObjectParam createObjectParam : createObjectData) {
            if ("serviceImpl".equals(createObjectParam.getFileType())) {
                fileName = createObjectParam.getFileName();
                filePath = createObjectParam.getFilePath();
                filePackageName = createObjectParam.getPackageName();
            }
            if ("service".equals(createObjectParam.getFileType())) {
                implementsInterface = createObjectParam.getFileName();
            }
            if ("dao".equals(createObjectParam.getFileType())) {
                callMapperType = createObjectParam.getFileName();
                callMapperPackage = createObjectParam.getPackageName();
            }
        }
        List<CreateMethodParam> createMethodParam = getCreateMethodParam();
        ClassGenerator.createClass(filePath, fileName, filePackageName, createMethodParam, implementsInterface, callMapperType, callMapperPackage);
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
    private static void createInterfaceTest(String interfaceName, String filePath, String filePackage, String fileType) {
        List<CreateMethodParam> createMethodParam = getCreateMethodParam();
        InterfaceGenerator.createInterface(filePath, interfaceName, filePackage, createMethodParam, fileType);
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
