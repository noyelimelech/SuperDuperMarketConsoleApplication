package SDM.Exception;

import javax.print.DocFlavor;

public class FileNotEndWithXMLException extends Exception
{
    private String fileType;

    public FileNotEndWithXMLException(String fileType){
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
