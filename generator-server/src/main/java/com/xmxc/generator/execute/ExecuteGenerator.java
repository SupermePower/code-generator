package com.xmxc.generator.execute;

import com.xmxc.generator.generator.ClassGenerator;
import com.xmxc.generator.generator.EntityGenerator;
import com.xmxc.generator.generator.InterfaceGenerator;
import com.xmxc.generator.util.*;

import java.util.List;
import java.util.Map;

public class ExecuteGenerator {


    public static void main(String[] args) {

        ParserXMLHelper parserXMLHelper = new ParserXMLHelper();

        // 读取xml中创建方法配置
        List<CreateMethodParam> createMethodParams = parserXMLHelper.getCreateMethodsData();

        // 读取xml中创建对象配置
        List<CreateObjectParam> createObjectData = parserXMLHelper.getCreateObjectData();

        // 获取创建model数据
        Map<String, String> createModelData = parserXMLHelper.getCreateModelData();

        // 获取连接数据库数据
        Map<String, String> connectDBData = parserXMLHelper.getConnectDBData();

        // 创建业务接口，数据映射接口
        createInterface(createObjectData, createMethodParams, createModelData.get("name"), createModelData.get("package") + "." + createModelData.get("name"));

        // 创建业务实现
        createClassTest(createObjectData, createMethodParams, createModelData.get("package") + "." + createModelData.get("name"), createModelData.get("name"));

        // 获取数据库表结构相关数据
        List<Map<String, String>> mapList = DBUtil.query(connectDBData.get("url"), connectDBData.get("username"), connectDBData.get("password"), createModelData.get("table"));

        // model创建
        createModelTest(createModelData, mapList);

        // 创建mpper映射文件
        createMapperXmlTest(mapList, createMethodParams, createObjectData, createModelData);
    }

    /**
     * 创建数据映射xml文件
     *
     * @param mapList
     * @param createMethodParams
     * @param createObjectData
     */
    private static void createMapperXmlTest(List<Map<String, String>> mapList, List<CreateMethodParam> createMethodParams,
                                            List<CreateObjectParam> createObjectData, Map<String, String> createModelData) {
        String namespace = "";
        String fileName = "";
        String filePath = "";
        String modelName = "";
        for (CreateObjectParam createObjectParam : createObjectData) {
            if ("dao".equals(createObjectParam.getFileType())) {
                fileName = createObjectParam.getFileName() + ".xml";
                filePath = createObjectParam.getFilePath();
                namespace = createObjectParam.getPackageName() + "." + createObjectParam.getFileName();
                modelName = createModelData.get("package") + "." + createModelData.get("name");
            }
        }
        CreateXmlHelper.createMapperXml(namespace, mapList, fileName, filePath, modelName, createMethodParams, createModelData.get("table"));
    }

    /**
     * 创建dao与service
     *
     * @param createObjectData
     */
    private static void createInterface(List<CreateObjectParam> createObjectData, List<CreateMethodParam> createMethodParam,
                                        String modelName, String modelPath) {
        for (CreateObjectParam createObjectParam : createObjectData) {
            if (!"serviceImpl".equals(createObjectParam.getFileType())) {
                String interfaceName = createObjectParam.getFileName();
                String filePath = createObjectParam.getFilePath();
                String filePackage = createObjectParam.getPackageName();
                String fileType = createObjectParam.getFileType();
                createInterfaceTest(interfaceName, filePath, filePackage, fileType, createMethodParam, modelName, modelPath);
            }
        }
    }

    /**
     * 创建业务实现类
     */
    private static void createClassTest(List<CreateObjectParam> createObjectData, List<CreateMethodParam> createMethodParam,
                                        String modelPath, String modelName) {
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
        ClassGenerator.createClass(filePath, fileName, filePackageName, createMethodParam, implementsInterface,
                callMapperType, callMapperPackage, modelPath, modelName);
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
    private static void createInterfaceTest(String interfaceName, String filePath, String filePackage, String fileType,
                                            List<CreateMethodParam> createMethodParam, String modelName, String modelPath) {
        InterfaceGenerator.createInterface(filePath, interfaceName, filePackage, createMethodParam, fileType, modelName, modelPath);
    }
}
