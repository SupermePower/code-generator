package com.xmxc.generator.test;

import com.xmxc.generator.generator.ClassGenerator;
import com.xmxc.generator.generator.EntityGenerator;
import com.xmxc.generator.generator.InterfaceGenerator;
import com.xmxc.generator.util.*;
import java.util.List;
import java.util.Map;

public class GeneratorTest {


    public static void main(String[] args) {

        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();

        // 读取xml中创建方法配置
//        List<CreateMethodParam> createMethodParams = parserXMLHelper.getCreateMethodsData();
//
//        // 读取xml中创建对象配置
//        List<CreateObjectParam> createObjectData = parserXMLHelper.getCreateObjectData();
//
//        // 创建业务接口，数据映射接口
//        createInterface(createObjectData, createMethodParams);
//
//        // 创建业务实现
//        createClassTest(createObjectData, createMethodParams);
//
//        // 获取创建model数据
        Map<String, String> createModelData = parserXMLHelper.getCreateModelData();
//
//        // 获取数据库表结构相关数据
        List<Map<String, String>> mapList = DBUtil.query(createModelData.get("table"));

        // model创建
        createModelTest(createModelData, mapList);

        // 创建mpper映射文件
//        createMapperXmlTest(mapList, createMethodParams, createObjectData, createModelData);
    }

    /**
     * 创建数据映射xml文件
     *
     * @param mapList
     * @param createMethodParams
     * @param createObjectData
     */
    private static void createMapperXmlTest(List<Map<String, String>> mapList, List<CreateMethodParam> createMethodParams, List<CreateObjectParam> createObjectData, Map<String, String> createModelData) {
        String namespace = "";
        String fileName = "";
        String filePath = "";
        String modelName = "";
        String tableName = "";
        for (CreateObjectParam createObjectParam : createObjectData) {
            if ("dao".equals(createObjectParam.getFileType())) {
                fileName = createObjectParam.getFileName() + ".xml";
                filePath = createObjectParam.getFilePath();
                namespace = createObjectParam.getPackageName() + "." + createObjectParam.getFileName();
                modelName = createModelData.get("package") + "." + createModelData.get("name");
            }
        }
        CreateXmlHelper.createMapperXml(namespace, mapList, fileName, filePath, modelName, createMethodParams, tableName);
    }

    /**
     * 创建dao与service
     *
     * @param createObjectData
     */
    private static void createInterface(List<CreateObjectParam> createObjectData, List<CreateMethodParam> createMethodParam) {
        for (CreateObjectParam createObjectParam : createObjectData) {
            if (!"serviceImpl".equals(createObjectParam.getFileType())) {
                String interfaceName = createObjectParam.getFileName();
                String filePath = createObjectParam.getFilePath();
                String filePackage = createObjectParam.getPackageName();
                String fileType = createObjectParam.getFileType();
                createInterfaceTest(interfaceName, filePath, filePackage, fileType, createMethodParam);
            }
        }
    }

    /**
     * 创建业务实现类
     */
    private static void createClassTest(List<CreateObjectParam> createObjectData, List<CreateMethodParam> createMethodParam) {
        String filePath = "";
        String fileName = "";
        String callMapperType = "";
        String callMapperPackage = "";
        String implementsInterface = "";
        String filePackageName = "";
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
        ClassGenerator.createClass(filePath, fileName, filePackageName, createMethodParam, implementsInterface, callMapperType, callMapperPackage);
    }

    /**
     * 创建实体
     */
    private static void createModelTest(Map<String, String> createModelData, List<Map<String, String>> tableData) {
        EntityGenerator.createModel(createModelData, tableData);
    }

    /**
     * 创建接口
     */
    private static void createInterfaceTest(String interfaceName, String filePath, String filePackage, String fileType, List<CreateMethodParam> createMethodParam) {
        InterfaceGenerator.createInterface(filePath, interfaceName, filePackage, createMethodParam, fileType);
    }

}
