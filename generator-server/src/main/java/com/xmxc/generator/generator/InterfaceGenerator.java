package com.xmxc.generator.generator;

import com.xmxc.generator.util.FileHelper;
import com.xmxc.generator.util.CreateMethodParam;
import com.xmxc.generator.util.FileImportPackageHelper;
import com.xmxc.generator.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class InterfaceGenerator {


    /**
     * 获取创建文件内容字符串
     *
     * @param interfaceName   接口名称
     * @param methods         方法集合
     * @param filePackageName 包路径
     * @param fileType        文件磁盘路径
     * @return
     */
    private static String getCreateInterfaceStatement(String interfaceName, List<CreateMethodParam> methods, String filePackageName, String fileType, String modelName, String modelPath) {
        StringBuffer fileContent = new StringBuffer();
        FileImportPackageHelper.getFileHeader(methods, filePackageName, fileContent, fileType, modelPath);
        fileContent.append("dao".equals(fileType) ? "@Repository\n" : "");
        fileContent.append("public interface " + interfaceName + " {\n\n");

        getCommonMethods(fileContent, modelName);
        for (CreateMethodParam method : methods) {
            String returnType = method.getReturnType();
            returnType = returnType.substring(returnType.lastIndexOf(".") + 1);
            fileContent.append("    " + returnType + " " + method.getMethodName() + "(");
            String params = "";
            for (String methodParam : method.getParamList()) {
                String packageUrl = methodParam;
                String methodParamType = packageUrl.substring(packageUrl.lastIndexOf(".") + 1);
                params = methodParamType + " " + StringUtil.camelName(methodParamType) + ", " + params;
            }
            fileContent.append("".equals(params) ? "" : params.substring(0, params.length() - 2));
            fileContent.append(");\n\n");
        }
        fileContent.append("}");
        return fileContent.toString();
    }

    /**
     * 创建创建接口
     *
     * @param filePath      文件磁盘路径
     * @param interfaceName 接口名称
     * @param packageName   包名
     * @param methods       方法集合
     * @param fileType      文件类型
     * @param modelName     实体类型
     */
    public static void createInterface(String filePath, String interfaceName, String packageName, List<CreateMethodParam> methods, String fileType, String modelName, String modelPath) {
        String modelBody = getCreateInterfaceStatement(interfaceName, methods, packageName, fileType, modelName, modelPath);
        FileHelper fileHelper = new FileHelper();
        fileHelper.createMapperInterface(interfaceName, filePath, modelBody);
    }

    /**
     * 创建基础方法
     *
     * @param modelName 实体
     * @return
     */
    public static void getCommonMethods(StringBuffer fileContent, String modelName) {
        //查询全部
        fileContent.append("\tList<" + modelName + "> findAll();\n\n");
        //根据主键查询
        fileContent.append("\t" + modelName + " findById(String id);\n\n");
        //删除
        fileContent.append("\tint deleteById(String id);\n\n");
        //修改
        fileContent.append("\tint update(" + modelName + " " + StringUtil.camelName(modelName) + ");\n\n");
        //新增
        fileContent.append("\tint save(" + modelName + " " + StringUtil.camelName(modelName) + ");\n\n");
    }

}
