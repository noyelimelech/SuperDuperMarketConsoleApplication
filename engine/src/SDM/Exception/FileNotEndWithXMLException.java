package SDM.Exception;

public class FileNotEndWithXMLException extends Exception
{
    private final String fileType;

    public FileNotEndWithXMLException(String fileType){
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
