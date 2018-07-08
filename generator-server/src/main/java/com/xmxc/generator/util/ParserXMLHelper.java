package com.xmxc.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
@Slf4j
public class ParserXMLHelper {

    /**
     * 解析XML文档(method)
     *
     * @param fileName xml文件全路径名称
     */
    private static List<CreateMethodParam> parserMethodsXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        List<CreateMethodParam> methodData = new ArrayList<>();
        try {
            Document document = saxReader.read(inputXml);       //SAX生成和解析XML文档
            Element generator = document.getRootElement();    //获得根节点
            Element methods = generator.element("methods");
            for (Iterator i = methods.elementIterator(); i.hasNext(); ) {
                CreateMethodParam createMethodParam = new CreateMethodParam();
                Element employee = (Element) i.next();
                createMethodParam.setMethodName(employee.attributeValue("name"));
                createMethodParam.setReturnType(employee.attributeValue("returnType"));
                //获取方法参数
                List<String> paramList = new ArrayList<>();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element method = (Element) j.next();
                    paramList.add(method.attributeValue("package"));
                }
                createMethodParam.setParamList(paramList);
                methodData.add(createMethodParam);
            }
        } catch (DocumentException e) {
            log.error("解析XML文件失败 -> DocumentException ", e);
        }
        return methodData;
    }

    /**
     * 解析XML文档（model）
     *
     * @param fileName xml文件全路径名称
     */
    private static Map<String, String> parserModelXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        Map<String, String> createModelParam = new HashMap<>();
        try {
            Document document = saxReader.read(inputXml);       //SAX生成和解析XML文档
            Element generator = document.getRootElement();   //获得根节点
            Element object = generator.element("model");
            createModelParam.put("table", object.attributeValue("table"));
            createModelParam.put("name", object.attributeValue("name"));
            createModelParam.put("package", object.attributeValue("package"));
            createModelParam.put("filePath", object.attributeValue("filePath"));
        } catch (DocumentException e) {
            log.error("解析XML文件失败(获取创建model信息) -> DocumentException ", e);
        }
        return createModelParam;
    }

    /**
     * 解析Xml文档（Object）
     *
     * @param fileName xml文件所在目录
     */
    private List<CreateObjectParam> parserObjectXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        List<CreateObjectParam> createObjectParams = new ArrayList<>();
        try {
            Document document = saxReader.read(inputXml);       //SAX生成和解析XML文档
            Element generator = document.getRootElement();   //获得根节点
            Element model = generator.element("object");
            for (Iterator i = model.elementIterator(); i.hasNext(); ) {
                Element obj = (Element) i.next();
                CreateObjectParam createObjectParam = new CreateObjectParam();
                createObjectParam.setFilePath(obj.attributeValue("filePath"));
                createObjectParam.setFileName(obj.attributeValue("name"));
                createObjectParam.setPackageName(obj.attributeValue("package"));
                createObjectParam.setFileType(obj.attributeValue("type"));
                createObjectParams.add(createObjectParam);
            }
        } catch (DocumentException e) {
            log.error("解析XML文件失败(获取创建object信息) -> DocumentException ", e);
        }
        return createObjectParams;
    }

    /**
     * 获取创建model数据
     *
     * @return
     */
    public Map<String, String> getCreateModelData() {
        String path = this.getClass().getResource("/generator.xml").getPath();
        return parserModelXml(path);
    }

    /**
     * 获取创建model数据
     *
     * @return
     */
    public List<CreateObjectParam> getCreateObjectData() {
        String path = this.getClass().getResource("/generator.xml").getPath();
        return parserObjectXml(path);
    }

    /**
     * 获取创建methods数据
     */
    public List<CreateMethodParam> getCreateMethodsData() {
        String path = this.getClass().getResource("/generator.xml").getPath();
        return parserMethodsXml(path);
    }
}
