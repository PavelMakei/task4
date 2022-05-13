package by.makei.shop.model.service.impl;

import by.makei.shop.exception.DaoException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.dao.BaseDao;
import by.makei.shop.model.dao.ProductDao;
import by.makei.shop.model.dao.impl.BrandDaoImpl;
import by.makei.shop.model.dao.impl.ProductDaoImpl;
import by.makei.shop.model.dao.impl.ProductTypeDaoImpl;
import by.makei.shop.model.entity.Brand;
import by.makei.shop.model.entity.Product;
import by.makei.shop.model.entity.ProductType;
import by.makei.shop.model.service.ProductService;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


import static by.makei.shop.model.command.AttributeName.*;

public class ProductServiceImpl implements ProductService {
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

        int quantityInStock = Integer.parseInt(productData.get(QUANTITY_IN_STOCK));


//TODO if ()?
        try {
            productDao.create(product, photo, quantityInStock);
        } catch (
                DaoException e) {
            logger.log(Level.ERROR, "ProductService error while addNewProduct. {}", e.getMessage());
            throw new ServiceException("ProductService error while addNewProduct.", e);
        }
        //проверить и записать в резалт

//TODO заготовка! Заполнить!


        return result;
    }

    @Override
    public Map<String, String> getAllBrandsMap() throws ServiceException {
        BaseDao<Brand> brandDao = new BrandDaoImpl();
        Map<String, String> brandsMap = new TreeMap<>();
        try {
            List<Brand> brandsList = brandDao.findAll();
            for (Brand brand : brandsList) {
                brandsMap.put(brand.getBrandName(), String.valueOf(brand.getId()));
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while getAllBrandsMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
        Map<String, String> resultMap = new LinkedHashMap<>();
        for(Map.Entry<String,String> entry: brandsMap.entrySet()){
            resultMap.put(entry.getValue(), entry.getKey());
        }

        return resultMap;

    }

    @Override
    public Map<String, String> getAllTypesMap() throws ServiceException {
        BaseDao<ProductType> productTypeDao = new ProductTypeDaoImpl();
        Map<String, String> typesMap = new TreeMap<>();
        try {
            List<ProductType> productTypeList = productTypeDao.findAll();
            for (ProductType productType : productTypeList) {
                typesMap.put(productType.getTypeName(), String.valueOf(productType.getId()));
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while getAllTypesMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
        Map<String, String> resultMap = new LinkedHashMap<>();
        for(Map.Entry<String,String> entry: typesMap.entrySet()){
            resultMap.put(entry.getValue(), entry.getKey());
        }
        return resultMap;
    }

    @Override
    public List<Product> getAllProductList() throws ServiceException {
        BaseDao<Product> productTypeDao = new ProductDaoImpl();
        List<Product> productList = new ArrayList<>();
        try {
            productList = productTypeDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while getAllProductsList. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return productList;
    }

    @Override
    public Map<Product,String> getAllProductMap() throws ServiceException {
        ProductDao productTypeDao = new ProductDaoImpl();
        Map<Product,String> productQuantityMap;
        try {
            productQuantityMap = productTypeDao.findAllMap();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while getAllProductsMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return productQuantityMap;
    }

    @Override
    public Map<Product, String> findProductsByParam(Map<String, String> searchParamMap) throws ServiceException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        ProductDao productDao = new ProductDaoImpl();
        if(parameterValidator.validateAndCorrectSearchProductParam(searchParamMap)){
            logger.log(Level.INFO, "ProductService incorrect data in search param map. Corrected.");
        }
        Map<Product,String> productQuantityMap;
        //разобрать на инты

//        productQuantityMap = productDao.findBySearchParam(searchParamMap);


        return null;
    }


}
