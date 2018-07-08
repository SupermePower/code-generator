package com.xmxc.generator.util;

import java.util.List;

public class CreateMethodParam {

    /**方法名*/
    private String methodName;
    /**返回类型*/
    private String returnType;
    /**参数集合*/
    private List<String> paramList;

    public CreateMethodParam() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    @Override
    public String toString() {
        return "CreateMethodParam{" +
                "methodName='" + methodName + '\'' +
                ", returnType='" + returnType + '\'' +
                ", paramList=" + paramList +
                '}';
    }
}
