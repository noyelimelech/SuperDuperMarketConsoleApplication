package SDM.jaxb.schema;

import SDM.Item;
import SDM.Location;
import SDM.Store;
import SDM.StoreItem;
import SDM.jaxb.schema.generated.SDMItem;
import SDM.jaxb.schema.generated.SDMSell;
import SDM.jaxb.schema.generated.SDMStore;
import SDM.jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLHandlerBaseOnSchema
{
    private List<Store> stores = null;
    private Map<Integer,Item> items =null;

    private static String JAXB_XML_PACKAGE_NAME="SDM.jaxb.schema.generated";

    public SuperDuperMarketDescriptor fromStringPathToDescriptor(String inpPath)throws FileNotFoundException,JAXBException
    {
            InputStream inputStream=new FileInputStream(new File(inpPath));
            SuperDuperMarketDescriptor sdmObj = deserialize(inputStream);
            return (sdmObj);
    }

    //deserialize from input to SuperDuperMarket
    private static SuperDuperMarketDescriptor deserialize(InputStream in)throws JAXBException
    {
        JAXBContext jc= JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u=jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }


    public void parseFromSDMItemToItem(SuperDuperMarketDescriptor sdmObj)
    {
        List<SDMItem> sdmItems= sdmObj.getSDMItems().getSDMItem();
        this.items=new HashMap<>();
        Item item;

        for (SDMItem sdmItem:sdmItems)
        {
            item=new Item(sdmItem.getId(),sdmItem.getName());
            item.checkAndUpdateItemType(sdmItem.getPurchaseCategory());//צריך לבדוק אם לא נכון הטייפ ולזרוק אקספשיין
            this.items.put(item.getId(),item);
        }
    }


    public void parseFromSDMStoresToStores(SuperDuperMarketDescriptor sdmObj)
    {
        List<SDMStore> sdmStores=  sdmObj.getSDMStores().getSDMStore();
        this.stores=new ArrayList<>();
        Store st;

        for (SDMStore sdmSt:sdmStores)
        {
            st=new Store();

            st.setId(sdmSt.getId());
            st.setName(sdmSt.getName());
            st.setDeliveryPPK(sdmSt.getDeliveryPpk());
            st.setLocation(new Location(new Point(sdmSt.getLocation().getX(),sdmSt.getLocation().getY())));
            Map<Integer,StoreItem> itemsInStStore= this.getStorItemesFromsdmPrices(sdmSt,st);
            st.setItemsThatSellInThisStore(itemsInStStore);
            this.stores.add(st);
        }
    }

    //convert sdmPrices to storeItem
    private Map<Integer, StoreItem> getStorItemesFromsdmPrices(SDMStore sdmSt, Store st)
    {
        List<SDMSell> listSDMSell =sdmSt.getSDMPrices().getSDMSell();
        Map<Integer,StoreItem> retMapStoreItems=new HashMap<>();
        StoreItem sti;

        for (SDMSell sdmSell:listSDMSell)
        {
            sti=new StoreItem();
            sti.setPrice(sdmSell.getPrice());
            sti.setStore(st);
            sti.setItem(this.items.get(sdmSell.getItemId()));
            retMapStoreItems.put(sti.getItem().getId(),sti);
        }
        return (retMapStoreItems);
    }





}
