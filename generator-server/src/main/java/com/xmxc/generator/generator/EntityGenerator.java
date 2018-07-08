package com.xmxc.generator.generator;

import com.xmxc.generator.util.CreateFileHelper;
import com.xmxc.generator.util.DBUtil;
import com.xmxc.generator.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class EntityGenerator {


    /**
     * 创建实体内容
     *
     * @param param       数据库表属性
     * @param className   文件名
     * @param packageName 文件所在包
     * @return
     */
    private static String getModelBody(List<Map<String, String>> param, String className, String packageName) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("package " + packageName + ";\n\n");
        Set<String> importPackage = getImportPackage(param);
        for (String packages : importPackage) {
            stringBuffer.append(packages);
        }
        stringBuffer.append("\n\n");
        stringBuffer.append("@Setter\n");
        stringBuffer.append("@Getter\n");
        stringBuffer.append("public class " + className + " {\n");
        for (Map<String, String> map : param) {
            stringBuffer.append("   /**" + map.get("column_comment") + "*/\n");
            stringBuffer.append("   private " + getPropertyType(map.get("data_type")) + " " + StringUtil.camelName(map.get("column_name") + ";\n"));
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    /**
     * 创建model
     *
     * @param createModelData
     */
    public static void createModel(Map<String, String> createModelData) {
        DBUtil dbUtil = new DBUtil();
        List<Map<String, String>> tableData = dbUtil.query(createModelData.get("table"));
        String modelBody = getModelBody(tableData, createModelData.get("name"), createModelData.get("package"));
        CreateFileHelper createFileHelper = new CreateFileHelper();
        createFileHelper.createMapperInterface(createModelData.get("name"), createModelData.get("filePath"), modelBody);
    }

    /**
     * 获取Mysql数据类型与java对应数据类型
     *
     * @param dbType 数据库数据类型
     * @return
     */
    private static String getPropertyType(String dbType) {
        switch (dbType) {
            case "int":
                dbType = "Integer";
                break;
            case "varchar":
                dbType = "String";
                break;
            case "datetime":
                dbType = "Timestamp";
                break;
            case "blob":
                dbType = "byte[]";
                break;
            case "tinyblob":
                dbType = "byte[]";
                break;
            case "binary":
                dbType = "byte[]";
                break;
            case "longblob":
                dbType = "byte[]";
                break;
            case "mediumblob":
                dbType = "byte[]";
                break;
            case "date":
                dbType = "Date";
                break;
            case "time":
                dbType = "Time";
                break;
            case "timestamp":
                dbType = "Timestamp";
                break;
            case "bigint":
                dbType = "Long";
                break;
            case "decimal":
                dbType = "BigDecimal";
                break;
            case "double":
                dbType = "Double";
                break;
            case "float":
                dbType = "Float";
                break;
            case "smallint":
                dbType = "Double";
                break;
            case "tinyint":
                dbType = "Byte";
                break;
            case "char":
                dbType = "String";
                break;
            case "longtext":
                dbType = "String";
                break;
            case "mediumtext":
                dbType = "String";
                break;
            case "bit":
                dbType = "Boolean";
                break;
            default:
                dbType = "Object";
                break;
        }
        return dbType;
    }

    /**
     * 获取导入package
     *
     * @param param 导包类型集合
     * @return
     */
    private static Set<String> getImportPackage(List<Map<String, String>> param) {
        Set<String> packages = new HashSet<>();
        for (int i = 0; i < param.size(); i++) {
            String propertyType = getPropertyType(param.get(i).get("data_type"));
            if ("Date".equals(propertyType)) {
                packages.add("import java.sql.Date;\n");
            } else if ("Time".equals(propertyType)) {
                packages.add("import java.sql.Time;\n");
            } else if ("Timestamp".equals(propertyType)) {
                packages.add("import java.sql.Timestamp;\n");
            } else if ("BigDecimal".equals(propertyType)) {
                packages.add("import java.math.BigDecimal;\n");
            }
        }
        packages.add("import lombok.Setter;\n");
        packages.add("import lombok.Getter;\n");
        return packages;
    }
}
