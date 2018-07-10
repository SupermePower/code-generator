package com.xmxc.generator.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * 创建xml文件
 */
public class CreateXmlHelper {

    /**
     * 创建xml文件
     *
     * @param tableData
     * @param filePath
     * @param fileName
     * @param namespace
     * @param modelName
     */
    private static void createXml(List<Map<String, String>> tableData, String filePath, String fileName, String namespace, String modelName, List<CreateMethodParam> methods, String tableName) {
        //创建一个xml文档
        Document doc = DocumentHelper.createDocument();
        //创建一个名为mapper的节点，因为是第一个创建，所以是根节点,再通过doc创建一个则会报错。
        Element mapper = doc.addElement("mapper");
        mapper.addAttribute("namespace", namespace);

        //获取result集合
        getResultMapper(tableData, modelName, mapper, fileName);

        for (CreateMethodParam createMethodParam : methods) {
            //获取根据主键获取元素信息
            String methodName = createMethodParam.getMethodName();
            if (methodName.startsWith("query") || methodName.startsWith("get") || methodName.startsWith("select")) {
                Element select = mapper.addElement("select");
                select.addAttribute("id", methodName);
                select.addAttribute("parameterType", "int");
                select.addAttribute("resultType", createMethodParam.getReturnType());
                select.addCDATA(" \n select * from " + tableName + " where id = #{id} ");
            }
            //删除
            if (methodName.startsWith("del")) {
                Element delete = mapper.addElement("delete");
                delete.addAttribute("id", "deleteById");
                if (createMethodParam.getParamList().size() == 1) {
                    delete.addAttribute("parameterType", createMethodParam.getParamList().get(0));
                    delete.addCDATA(" \n update " + tableName + " set is_del = '0' " + " where id = #{id} ");
                }
            }
        }
        //将xml对象写入文件
        FileHelper fileHelper = new FileHelper();
        fileHelper.writeXml(doc, filePath, fileName);
    }

    /**
     * 获取resultMapper
     *
     * @param tableData
     * @param modelName
     * @param mapper
     */
    private static void getResultMapper(List<Map<String, String>> tableData, String modelName, Element mapper, String fileName) {
        //在mapper节点下创建一个名为resultMap的节点
        Element resultMap = mapper.addElement("resultMap");
        //给resultMap节点添加属性
        resultMap.addAttribute("id", StringUtil.camelName(fileName).substring(0, StringUtil.camelName(fileName).indexOf(".")));
        resultMap.addAttribute("type", modelName);
        for (Map<String, String> map : tableData) {
            if ("PRI".equals(map.get("column_key"))) {
                Element id = resultMap.addElement("id");
                id.addAttribute("column", map.get("column_name"));
                id.addAttribute("property", StringUtil.camelName(map.get("column_name")));
            } else {
                //设置字段映射
                Element result = resultMap.addElement("result");
                result.addAttribute("column", map.get("column_name"));
                result.addAttribute("property", StringUtil.camelName(map.get("column_name")));
            }
        }
    }

    /**
     * 创建xml映射文件
     *
     * @param namespace
     * @param mapList
     * @param fileName
     * @param filePath
     * @param modelName
     * @param methods
     * @param tableName
     */
    public static void createMapperXml(String namespace, List<Map<String, String>> mapList, String fileName, String filePath, String modelName, List<CreateMethodParam> methods, String tableName) {
        createXml(mapList, filePath, fileName, namespace, modelName, methods, tableName);
    }
}
