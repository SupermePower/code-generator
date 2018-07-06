package com.xmxc.generator.generator;

import com.xmxc.generator.util.CreateFileHelper;
import com.xmxc.generator.util.FileImportPackageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class InterfaceGenerator {


    /**
     * 获取创建文件内容字符串
     *
     * @param interfaceName        文件名称
     * @param methodParam     方法参数
     * @param filePackageName 文件包
     * @return
     */
    private static String getCreateInterfaceStatement(String interfaceName, Map<String, String> methodParam, String filePackageName, String returnType) {
        StringBuffer fileContent = new StringBuffer();
        FileImportPackageHelper.getFileHeader(methodParam, filePackageName, fileContent, "mapper");
        fileContent.append("@Repository\n");
        fileContent.append("public interface " + interfaceName + " {\n\n");
        for (String methodName : methodParam.keySet()) {
            String packageUrl = methodParam.get(methodName);
            String methodParamType = packageUrl.substring(packageUrl.lastIndexOf(".") + 1);
            returnType = returnType.substring(returnType.lastIndexOf(".") + 1);
            fileContent.append("    " + returnType + " " + methodName + "(" + methodParamType + " param);\n\n");
        }
        fileContent.append("}");
        return fileContent.toString();
    }

    /**
     * 创建创建接口
     *
     * @param classPath 文件创建路径（磁盘目录）
     * @param interfaceName 接口名称
     * @param packageName 包名
     */
    public static void createInterface(String classPath, String interfaceName, String packageName, Map<String, String> methodParam, String returnType) {
        String modelBody = getCreateInterfaceStatement(interfaceName, methodParam, packageName, returnType);
        CreateFileHelper createFileHelper = new CreateFileHelper();
        createFileHelper.createMapperInterface(interfaceName, classPath, modelBody);
    }

}
