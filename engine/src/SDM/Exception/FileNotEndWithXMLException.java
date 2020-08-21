package SDM.Exception;

import javax.print.DocFlavor;

public class FileNotEndWithXMLException extends Exception
{
    String fileType;
    public FileNotEndWithXMLException(String fileType){
        this.fileType = fileType;
    }

}
