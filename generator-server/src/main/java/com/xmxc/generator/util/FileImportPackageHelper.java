package com.xmxc.generator.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileImportPackageHelper {

    /**
     * 获取文件头部
     *
     * @param methodParam
     * @param filePackageName
     * @param fileContent
     */
    public static void getFileHeader(List<CreateMethodParam> methodParam, String filePackageName, StringBuffer fileContent, String fileType) {
        fileContent.append("package " + filePackageName + " ;\n\n");
        Set<String> packageSet = new HashSet<>();
        for (CreateMethodParam createMethodParam : methodParam) {
            String returnType = createMethodParam.getReturnType();
            for (String param : createMethodParam.getParamList()) {
                String packageUrl = param;
                String paramType = packageUrl.substring(packageUrl.lastIndexOf(".") + 1);
                if (!paramType.equals("byte") && !paramType.equals("short") && !paramType.equals("int") && !paramType.equals("float")
                        && !paramType.equals("double") && !paramType.equals("char") && !paramType.equals("boolean") && !paramType.equals("long")) {
                    packageSet.add("import " + packageUrl + ";\n\n");
                }
            }
            if (!"void".equals(returnType)) {
                packageSet.add("import " + returnType + ";\n\n");
            }
        }
        for (String packageName : packageSet) {
            fileContent.append(packageName);
        }
        if ("serviceImpl".equals(fileType)) {
            fileContent.append("import org.springframework.stereotype.Service;\n");
            fileContent.append("import org.springframework.beans.factory.annotation.Autowired;\n\n");
        }
        if ("dao".equals(fileType)) {
            fileContent.append("import org.springframework.stereotype.Repository;\n\n");
        }
    }

}
