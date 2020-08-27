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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLHandlerBaseOnSchema
{
    private List<Store> stores = null;
    private Map<Integer,Item> items =null;

    private String JAXB_XML_PACKAGE_NAME="SDM.jaxb.schema.generated";


    public void updateStoresAndItems(String stPath) throws FileNotFoundException, JAXBException, FileNotEndWithXMLException, DuplicateItemException, LocationIsOutOfBorderException, DuplicateStoreIDException, DuplicateStoreItemException, TryingToGivePriceOfItemWhichIDNotExistException, TryingToGiveDiffrentPricesForSameStoreItemException {
        SuperDuperMarketDescriptor sdmDescriptor=this.fromStringPathToDescriptor(stPath);
        parseFromSDMItemToItem(sdmDescriptor);
        parseFromSDMStoresToStores(sdmDescriptor);
    }

    private SuperDuperMarketDescriptor fromStringPathToDescriptor(String inpPath) throws FileNotFoundException, JAXBException, FileNotEndWithXMLException
    {
        File inputFile = new File(inpPath);
        if(!inputFile.exists()) {
            throw new FileNotFoundException();
        }

        if(inpPath.length()-4!=(inpPath.toLowerCase().lastIndexOf(".xml")))
        {
            throw (new FileNotEndWithXMLException(inpPath.substring(inpPath.length()-3)));
        }

        InputStream inputStream = new FileInputStream(new File(inpPath));
        SuperDuperMarketDescriptor sdmObj = deserialize(inputStream);
        return (sdmObj);

    }

    //deserialize from input to SuperDuperMarket
    private SuperDuperMarketDescriptor deserialize(InputStream in)throws JAXBException
    {
        JAXBContext jc= JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u=jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }

    public List<Store> getStores()
    {
        return stores;
    }

    public Map<Integer, Item> getItems()
    {
        return items;
    }

    private void parseFromSDMItemToItem(SuperDuperMarketDescriptor sdmObj) throws DuplicateItemException
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
            item.checkAndUpdateItemType(sdmItem.getPurchaseCategory());
            this.items.put(item.getId(),item);
        }
    }


    private void parseFromSDMStoresToStores(SuperDuperMarketDescriptor sdmObj) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException, TryingToGivePriceOfItemWhichIDNotExistException, TryingToGiveDiffrentPricesForSameStoreItemException {
        List<SDMStore> sdmStores=  sdmObj.getSDMStores().getSDMStore();
        this.stores=new ArrayList<>();
        Store st;

        for (SDMStore sdmSt:sdmStores)
        {
            //st=new Store();

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
            
            boolean flagIsItLegalLocation=Location.checkIfIsLegalLocation(sdmSt.getLocation().getX(),sdmSt.getLocation().getY());
            if(!flagIsItLegalLocation)
            {
                throw (new LocationIsOutOfBorderException(Location.minBorder,Location.maxBorder, "Store" , sdmSt.getId() ));
            }
            
            st.setLocation(new Location(new Point(sdmSt.getLocation().getX(),sdmSt.getLocation().getY())));
            Map<Integer,StoreItem> itemsInStStore= this.getStorItemesFromsdmPrices(sdmSt,st);
            st.setItemsThatSellInThisStore(itemsInStStore);
            this.stores.add(st);
        }
    }
/*
    private boolean checkIfIsLegalLocation(int x, int y)
    {
        return((x>=Location.minBorder&&x<=Location.maxBorder) && (y>=Location.minBorder&&y<=Location.maxBorder));
    }

 */

    //convert sdmPrices to storeItem
    private Map<Integer, StoreItem> getStorItemesFromsdmPrices(SDMStore sdmSt, Store st) throws DuplicateStoreItemException, TryingToGivePriceOfItemWhichIDNotExistException, TryingToGiveDiffrentPricesForSameStoreItemException {
        List<SDMSell> listSDMSell =sdmSt.getSDMPrices().getSDMSell();
        Map<Integer,StoreItem> retMapStoreItems=new HashMap<>();
        StoreItem sti;

        for (SDMSell sdmSell:listSDMSell)
        {
            if(retMapStoreItems.containsKey(sdmSell.getItemId()))
            {
               throw (new DuplicateStoreItemException(sdmSell.getItemId()));
            }
            

            //////NOY HADASH
            if(this.items.containsKey(sdmSell.getItemId()))
            {
                sti=new StoreItem();
                if (sti.getPrice()!=0)
                {
                    throw (new TryingToGiveDiffrentPricesForSameStoreItemException(st.getId()));
                }
                sti.setPrice(sdmSell.getPrice());
                sti.setStore(st);
                sti.setItem(this.items.get(sdmSell.getItemId()));
                retMapStoreItems.put(sti.getItem().getId(),sti);
            }
            else
                throw (new TryingToGivePriceOfItemWhichIDNotExistException(sdmSell.getItemId()));
        }
        return (retMapStoreItems);
    }





}
