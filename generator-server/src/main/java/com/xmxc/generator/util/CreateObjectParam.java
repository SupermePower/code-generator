package com.xmxc.generator.util;

public class CreateObjectParam {

    private String fileName;
    private String packageName;
    private String filePath;
    private String fileType;

    public CreateObjectParam() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "CreateObjectParam{" +
                "fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
