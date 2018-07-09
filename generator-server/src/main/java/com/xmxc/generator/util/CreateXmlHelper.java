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
    private void createXml(List<Map<String, String>> tableData, String filePath, String fileName, String namespace, String modelName) {
        //创建一个xml文档
        Document doc = DocumentHelper.createDocument();
        //创建一个名为students的节点，因为是第一个创建，所以是根节点,再通过doc创建一个则会报错。
        Element mapper = doc.addElement("mapper");
        mapper.addAttribute("namespace", namespace);

        //获取result集合
        getResultMapper(tableData, modelName, mapper);

        //获取根据主键获取元素信息
        Element select = mapper.addElement("select");
        select.addAttribute("id", "selectXXXById");
        select.addAttribute("parameterType", "int");
        select.addAttribute("resultType", "com.xmxc.generator.Demo");
        select.setText("select * from " + "tableName" + " where id = #{id}");
        //删除
        Element delete = mapper.addElement("delete");
        delete.addAttribute("id", "deleteById");
        delete.addAttribute("parameterType", "int");
        delete.setText("update tableName set is_del = '0' " + " where id = #{id}");

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
    private void getResultMapper(List<Map<String, String>> tableData, String modelName, Element mapper) {
        //在mapper节点下创建一个名为resultMap的节点
        Element resultMap = mapper.addElement("resultMap");
        //给resultMap节点添加属性
        resultMap.addAttribute("id", "userOrderMap");
        resultMap.addAttribute("type", StringUtil.camelName(modelName));
        for (Map<String, String> map : tableData) {
            if ("PRI".equals(map.get("column_key"))) {
                //添加字段映射（主键）
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

    public static void main(String[] args) {
        String namespace = "com.xiongmaoxingchu.shop.wxminiprogramorder.dao.OrderMapper";
        String mapperType = "";
        String id = "";
        String tableName = "t_food_store";
        String fileName = "test.xml";
        String filePath = "E:/work/test";
        String modelName = "com.xiongmaoxingchu.shop.wxminiprogramorder.model.vo.OrderVo";
        CreateXmlHelper xml = new CreateXmlHelper();
        DBUtil dbUtil = new DBUtil();
        xml.createXml(dbUtil.query(tableName), filePath, fileName, namespace, modelName);
    }
}
