package SDM.jaxb.schema;

import SDM.jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SchemaBasedOnJAXBMain
{
    private static String JAXB_XML_PACKAGE_NAME="SDM.jaxb.schema.generated";

    public static void main(String[] args)
    {
        try
        {
            InputStream inputStream=new FileInputStream(new File("C:\\Users\\User\\Desktop\\javaExtraFilesPROJ1\\ex1-small.xml"));
            SuperDuperMarketDescriptor sdmObj =deserializeStore(inputStream);
            System.out.println("name of first store is: :"+ sdmObj.getSDMStores().getSDMStore().get(0).getName());
        }
        catch (FileNotFoundException | JAXBException e)
        {
            e.printStackTrace();
        }
    }

    private static SuperDuperMarketDescriptor deserializeStore(InputStream in)throws JAXBException
    {
        JAXBContext jc= JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u=jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);

    }

}
