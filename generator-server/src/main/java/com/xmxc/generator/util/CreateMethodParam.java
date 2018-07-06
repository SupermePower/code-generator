package com.xmxc.generator.util;

import java.util.List;

public class CreateMethodParam {

    private String methodName;
    private String returnType;
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
