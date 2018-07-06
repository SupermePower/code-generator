package com.xmxc.generator.generator;

import com.xmxc.generator.util.CreateFileHelper;
import com.xmxc.generator.util.FileImportPackageHelper;
import com.xmxc.generator.util.StringUtil;

import java.util.Map;

public class ClassGenerator {

    /**
     * 获取创建文件内容字符串
     *
     * @param fileName        文件名称
     * @param methodParam     方法参数
     * @param filePackageName 文件包
     * @return
     */
    private static String getCreateClassStatement(String fileName, Map<String, String> methodParam, String filePackageName,
                                                  String implementsInterface, String returnType, String callMapperType) {
        StringBuffer fileContent = new StringBuffer();
        FileImportPackageHelper.getFileHeader(methodParam, filePackageName, fileContent, "service");
        fileContent.append("@Service\n");
        fileContent.append("public class " + fileName + " implements " + implementsInterface + " {\n\n");
        callMapperType = callMapperType.substring(callMapperType.lastIndexOf(".") + 1);
        fileContent.append("    @Autowired\n");
        fileContent.append("    private " + callMapperType + " " + StringUtil.camelName(callMapperType) + ";\n\n");
        for (String methodName : methodParam.keySet()) {
            String packageUrl = methodParam.get(methodName);
            if (!"void".equals(returnType)) {
                returnType = returnType.substring(returnType.lastIndexOf(".") + 1);
                String methodParamType = packageUrl.substring(packageUrl.lastIndexOf(".") + 1);
                fileContent.append("    public " + returnType + " " + methodName + "(" + methodParamType + " param) {\n" +
                        "    }\n\n");
            } else {
                String methodParamType = packageUrl.substring(packageUrl.lastIndexOf(".") + 1);
                fileContent.append("    public void " + methodName + "(" + methodParamType + " param) {\n" +
                        "    }\n\n");
            }
        }
        fileContent.append("}");
        return fileContent.toString();
    }

    /**
     * 创建Class
     *
     * @param classPath 文件创建路径（磁盘目录）
     * @param className 文件名称
     * @param packageName 包名
     */
    public static void createClass(String classPath, String className, String packageName, Map<String, String> methodParam,
                                   String implementsInterface, String returnType, String callMapperType) {
        String modelBody = getCreateClassStatement(className, methodParam, packageName, implementsInterface, returnType, callMapperType);
        CreateFileHelper createFileHelper = new CreateFileHelper();
        createFileHelper.createMapperInterface(className, classPath, modelBody);
    }
}
