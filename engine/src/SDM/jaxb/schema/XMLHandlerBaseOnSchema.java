package SDM.jaxb.schema;

import SDM.*;
import SDM.Exception.*;
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

    public SuperDuperMarketDescriptor fromStringPathToDescriptor(String inpPath) throws FileNotFoundException, JAXBException, FileNotEndWithXMLException
    {

        if(inpPath.length()-4!=(inpPath.toLowerCase().lastIndexOf(".xml")))
        {
            throw (new FileNotEndWithXMLException());
        }

        InputStream inputStream = new FileInputStream(new File(inpPath));
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


    public void parseFromSDMItemToItem(SuperDuperMarketDescriptor sdmObj) throws DuplicateItemException
    {
        List<SDMItem> sdmItems= sdmObj.getSDMItems().getSDMItem();
        this.items=new HashMap<>();
        Item item;

        for (SDMItem sdmItem:sdmItems)
        {
            if(this.items.containsKey(sdmItem.getId()))
            {
                throw (new DuplicateItemException(sdmItem.getId()));
            }
            item=new Item(sdmItem.getId(),sdmItem.getName());
            item.checkAndUpdateItemType(sdmItem.getPurchaseCategory());//צריך לבדוק אם לא נכון הטייפ ולזרוק אקספשיין
            this.items.put(item.getId(),item);
        }
    }


    public void parseFromSDMStoresToStores(SuperDuperMarketDescriptor sdmObj) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException {
        List<SDMStore> sdmStores=  sdmObj.getSDMStores().getSDMStore();
        this.stores=new ArrayList<>();
        Store st;

        for (SDMStore sdmSt:sdmStores)
        {
            st=new Store();

            for (Store s:this.stores)
            {
                if(s.getId()==sdmSt.getId())
                {
                    throw (new DuplicateStoreIDException(sdmSt.getId()));
                }
            }

            st=new Store();
            st.setId(sdmSt.getId());
            st.setName(sdmSt.getName());
            st.setDeliveryPPK(sdmSt.getDeliveryPpk());
            
            boolean flagIsItLegalLocation= checkIfIsLegalLocation(sdmSt.getLocation().getX(),sdmSt.getLocation().getY());
            if(!flagIsItLegalLocation)
            {
                throw (new LocationIsOutOfBorderException(Location.minBorder,Location.maxBorder));
            }
            
            st.setLocation(new Location(new Point(sdmSt.getLocation().getX(),sdmSt.getLocation().getY())));
            Map<Integer,StoreItem> itemsInStStore= this.getStorItemesFromsdmPrices(sdmSt,st);
            st.setItemsThatSellInThisStore(itemsInStStore);
            this.stores.add(st);
        }
        
    }

    public boolean checkIfIsLegalLocation(int x, int y)
    {
        return((x>=Location.minBorder&&x<=Location.minBorder) && (y>=Location.minBorder&&y<=Location.maxBorder));
    }

    //convert sdmPrices to storeItem
    private Map<Integer, StoreItem> getStorItemesFromsdmPrices(SDMStore sdmSt, Store st) throws DuplicateStoreItemException
    {
        List<SDMSell> listSDMSell =sdmSt.getSDMPrices().getSDMSell();
        Map<Integer,StoreItem> retMapStoreItems=new HashMap<>();
        StoreItem sti;

        for (SDMSell sdmSell:listSDMSell)
        {
            if(retMapStoreItems.containsKey(sdmSell.getItemId()))
            {
                throw (new DuplicateStoreItemException(sdmSell.getItemId()));
            }
            
            sti=new StoreItem();
            sti.setPrice(sdmSell.getPrice());
            sti.setStore(st);
            sti.setItem(this.items.get(sdmSell.getItemId()));
            retMapStoreItems.put(sti.getItem().getId(),sti);
        }
        return (retMapStoreItems);
    }





}
