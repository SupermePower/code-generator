package com.xmxc.generator.generator;

import com.xmxc.generator.util.CreateMethodParam;
import com.xmxc.generator.util.FileHelper;
import com.xmxc.generator.util.FileImportPackageHelper;
import com.xmxc.generator.util.StringUtil;

import java.util.List;

public class ClassGenerator {

    /**
     * 获取创建文件内容字符串
     *
     * @param fileName            文件名称
     * @param methods             方法集合
     * @param filePackageName     包名
     * @param implementsInterface 实现接口名称
     * @param callMapperType      调用接口名称
     * @param callMapperPackage   调用接口所在包路径
     * @return
     */
    private static String getCreateClassStatement(String fileName, List<CreateMethodParam> methods, String filePackageName,
                                                  String implementsInterface, String callMapperType, String callMapperPackage, String modelPath, String modelName) {
        StringBuffer fileContent = new StringBuffer();
        FileImportPackageHelper.getFileHeader(methods, filePackageName, fileContent, "serviceImpl", modelPath);
        fileContent.append("import " + callMapperPackage + "." + callMapperType + ";\n\n");
        fileContent.append("@Service\n");
        fileContent.append("public class " + fileName + " implements " + implementsInterface + " {\n\n");
        callMapperType = callMapperType.substring(callMapperType.lastIndexOf(".") + 1);
        fileContent.append("    @Autowired\n");
        fileContent.append("    private " + callMapperType + " " + StringUtil.camelName(callMapperType) + ";\n\n");
        getCommonMethods(fileContent, modelName, callMapperType);
        for (CreateMethodParam method : methods) {
            String returnType = method.getReturnType();
            if (!"void".equals(returnType)) {
                returnType = returnType.substring(returnType.lastIndexOf(".") + 1);
                fileContent.append("@Override\n");
                fileContent.append("    public " + returnType + " " + method.getMethodName() + "(");
            } else {
                fileContent.append("@Override\n");
                fileContent.append("    public void " + method.getMethodName() + "(");
            }
            String params = "";
            String callParam = "";
            for (String methodParam : method.getParamList()) {
                String methodParamType = methodParam.substring(methodParam.lastIndexOf(".") + 1);
                params = methodParamType + " " + StringUtil.camelName(methodParamType) + ", " + params;
                callParam = StringUtil.camelName(methodParamType) + ", " + callParam;
            }
            fileContent.append("".equals(params) ? "" : params.substring(0, params.length() - 2));
            fileContent.append(") {\n");
            if ("void".equals(method.getReturnType())) {
                if (!"".equals(callParam)) {
                    fileContent.append("        " + StringUtil.camelName(callMapperType) + "." + method.getMethodName() +
                            "(" + callParam.substring(0, callParam.length() - 2) + ")" + ";\n");
                } else {
                    fileContent.append("        " + StringUtil.camelName(callMapperType) + "." + method.getMethodName() +
                            "()" + ";\n");
                }
            } else {
                if (!"".equals(callParam)) {
                    fileContent.append("        return " + StringUtil.camelName(callMapperType) + "." + method.getMethodName() +
                            "(" + callParam.substring(0, callParam.length() - 2) + ");\n");
                } else {
                    fileContent.append("        return " + StringUtil.camelName(callMapperType) + "." + method.getMethodName() +
                            "();\n");
                }
            }
            fileContent.append("    }\n\n");
        }
        fileContent.append("}");
        return fileContent.toString();
    }

    /**
     * 创建Class
     *
     * @param filePath            文件磁盘路径
     * @param className           文件名称
     * @param packageName         包名
     * @param methods             方法集合
     * @param implementsInterface 实现接口名称
     * @param callMapperType      调用接口名称
     * @param callMapperPackage   调用接口所在包路径
     */
    public static void createClass(String filePath, String className, String packageName, List<CreateMethodParam> methods,
                                   String implementsInterface, String callMapperType, String callMapperPackage, String modelPath, String modelName) {
        String modelBody = getCreateClassStatement(className, methods, packageName, implementsInterface, callMapperType, callMapperPackage, modelPath, modelName);
        FileHelper fileHelper = new FileHelper();
        fileHelper.createMapperInterface(className, filePath, modelBody);
    }

    /**
     * 创建基础方法
     *
     * @param modelName 实体
     * @param callType  调用类型
     * @return
     */
    public static void getCommonMethods(StringBuffer fileContent, String modelName, String callType) {
        //查询全部
        fileContent.append("\t@Override\n");
        fileContent.append("\tpublic List<" + modelName + "> findAll() {\n");
        fileContent.append("\t\treturn " + StringUtil.camelName(callType) + ".findAll();\n");
        fileContent.append("\t}\n\n");
        //根据主键查询
        fileContent.append("\t@Override\n");
        fileContent.append("\tpublic " + modelName + " findById(String id) {\n");
        fileContent.append("\t\treturn " + StringUtil.camelName(callType) + ".findById(id);\n");
        fileContent.append("\t}\n\n");
        //删除
        fileContent.append("\t@Override\n");
        fileContent.append("\tpublic int deleteById(String id) {\n");
        fileContent.append("\t\treturn " + StringUtil.camelName(callType) + ".deleteById(id);\n");
        fileContent.append("\t}\n\n");
        //修改
        fileContent.append("\t@Override\n");
        fileContent.append("\tpublic int update(" + modelName + " " + StringUtil.camelName(modelName) + ") {\n");
        fileContent.append("\t\treturn " + StringUtil.camelName(callType) + ".update(" + StringUtil.camelName(modelName) + ");\n");
        fileContent.append("\t}\n\n");
        //新增
        fileContent.append("\t@Override\n");
        fileContent.append("\tpublic int save(" + modelName + " " + StringUtil.camelName(modelName) + ") {\n");
        fileContent.append("\t\treturn " + StringUtil.camelName(callType) + ".save(" + StringUtil.camelName(modelName) + ");\n");
        fileContent.append("\t}\n\n");
    }
}
