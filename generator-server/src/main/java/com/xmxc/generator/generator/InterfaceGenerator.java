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
    private static String getCreateInterfaceStatement(String interfaceName, List<CreateMethodParam> methods, String filePackageName, String fileType) {
        StringBuffer fileContent = new StringBuffer();
        FileImportPackageHelper.getFileHeader(methods, filePackageName, fileContent, fileType);
        fileContent.append("dao".equals(fileType) ? "@Repository\n" : "");
        fileContent.append("public interface " + interfaceName + " {\n\n");
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
     */
    public static void createInterface(String filePath, String interfaceName, String packageName, List<CreateMethodParam> methods, String fileType) {
        String modelBody = getCreateInterfaceStatement(interfaceName, methods, packageName, fileType);
        FileHelper fileHelper = new FileHelper();
        fileHelper.createMapperInterface(interfaceName, filePath, modelBody);
    }

}
