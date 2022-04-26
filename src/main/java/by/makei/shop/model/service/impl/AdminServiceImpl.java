package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.BaseDao;
import by.makei.shop.model.dao.impl.BrandDaoImpl;
import by.makei.shop.model.dao.impl.ProductDaoImpl;
import by.makei.shop.model.dao.impl.ProductTypeDaoImpl;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;
import by.makei.shop.model.service.AdminService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import static by.makei.shop.model.command.AttributeName.*;

public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean addNewProduct(Map<String, String> productData, byte[] photo) throws ServiceException {
        boolean result = true;
        Product product = new Product();
        ProductDaoImpl productDao = new ProductDaoImpl();

        product.setBrandId(Integer.parseInt(productData.get(BRAND_ID)));
        product.setTypeId(Integer.parseInt(productData.get(TYPE_ID)));
        product.setProductName((productData.get(PRODUCT_NAME)));
        product.setDescription((productData.get(DESCRIPTION)));
        product.setPrice(Double.parseDouble(productData.get(PRICE)));
        product.setColour(productData.get(COLOUR));
        product.setPower(Integer.parseInt(productData.get(POWER)));
        product.setSize(productData.get(SIZE));
        product.setPhoto(photo);

        int quantity = Integer.parseInt(productData.get(QUANTITY));


//TODO if ()?
        try {
        productDao.create(product,quantity);
    } catch (
    DaoException e) {
        logger.log(Level.ERROR, "AdminService error while addNewProduct. {}",e.getMessage());
        throw new ServiceException("AdminService error while addNewProduct.",e);
    }
        //проверить и записать в резалт

//TODO заготовка! Заполнить!


        return result;
    }

    @Override
    public Map<String, Integer> getAllBrandsMap() throws ServiceException {
        BaseDao<Brand> brandDao = new BrandDaoImpl();
        Map<String,Integer> brandsMap = new TreeMap<>();
        try {
            List<Brand> brandsList = brandDao.findAll();
            for (Brand brand:brandsList){
                brandsMap.put(brand.getBrandName(), brand.getId());
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "AdminService error while getAllBrandsMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
return brandsMap;
    }

    @Override
    public Map<String, Integer> getAllTypesMap() throws ServiceException{
        BaseDao<ProductType> productTypeDao = new ProductTypeDaoImpl();
        Map<String,Integer> typesMap = new TreeMap<>();
        try{
            List<ProductType> productTypeList = productTypeDao.findAll();
            for(ProductType productType:productTypeList){
                typesMap.put(productType.getTypeName(), productType.getId());
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "AdminService error while getAllTypesMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return typesMap;
    }

}
