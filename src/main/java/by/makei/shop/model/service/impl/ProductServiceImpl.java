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
import by.makei.shop.model.validator.AttributeValidator;
import by.makei.shop.model.validator.ParameterValidator;
import by.makei.shop.model.validator.impl.AttributeValidatorImpl;
import by.makei.shop.model.validator.impl.ParameterValidatorImpl;
import by.makei.shop.util.SqlUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;


import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.validator.DefaultSearchParam.PRODUCTS_ON_PAGE;

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
        for (Map.Entry<String, String> entry : brandsMap.entrySet()) {
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
        for (Map.Entry<String, String> entry : typesMap.entrySet()) {
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
    public Map<Product, String> getAllProductMap() throws ServiceException {
        ProductDao productTypeDao = new ProductDaoImpl();
        Map<Product, String> productQuantityMap;
        try {
            productQuantityMap = productTypeDao.findAllMap();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while getAllProductsMap. {}", e.getMessage());
            throw new ServiceException(e);
        }
        return productQuantityMap;
    }

    @Override
    public Map<Product, String> findProductsByParam(Map<String, String> searchParamMap, Map<String, String> orderByParamQuery) throws ServiceException {
        ParameterValidator parameterValidator = ParameterValidatorImpl.getInstance();
        ProductDao productDao = new ProductDaoImpl();
        Map<Product, String> productQuantityMap = new HashMap<>();
        if (parameterValidator.validateAndCorrectSearchProductParam(searchParamMap)) {
            logger.log(Level.INFO, "ProductService incorrect data in search param map. Corrected.");
        }

        int brandId = 0;
        int typeId = 0;
        int minPrice = 0;
        int maxPrice = 0;
        int minPower = 0;
        int maxPower = 0;
        int searchPage = 0;
        int searchPageIncrement = 0;
        String searchWord ="";
        String orderBy="";
        String orderQuery="";
        int totalProductsFound = 0;
        int totalPages = 0;

        for (Map.Entry<String, String> entry : searchParamMap.entrySet()) {
            switch (entry.getKey()) {
                case SEARCH_BRAND_ID -> {
                    brandId = Integer.parseInt(entry.getValue());
                }
                case SEARCH_TYPE_ID -> {
                    typeId = Integer.parseInt(entry.getValue());
                }
                case SEARCH_MIN_PRICE -> {
                    minPrice = Integer.parseInt(entry.getValue());
                }
                case SEARCH_MAX_PRICE -> {
                    maxPrice = Integer.parseInt(entry.getValue());
                }
                case SEARCH_MIN_POWER -> {
                    minPower = Integer.parseInt(entry.getValue());
                }
                case SEARCH_MAX_POWER -> {
                    maxPower = Integer.parseInt(entry.getValue());
                }
                case SEARCH_PAGE -> {
                    searchPage = Integer.parseInt(entry.getValue());
                }
                case SEARCH_WORD -> {
                    searchWord = SqlUtil.mysql_escape_string(entry.getValue());
                }
                case ORDER_BY -> {
                    orderBy = entry.getValue();
                }
                case PAGE_BUTTON -> {
                    if (entry.getValue().equals(NEXT_PAGE)) {
                        searchPageIncrement = 1;
                    } else if (entry.getValue().equals(PREVIOUS_PAGE)) {
                        searchPageIncrement = -1;
                    }
                }
            }
        }
        if (minPrice > maxPrice) {
            minPrice = maxPrice;
        }
        if (minPower > maxPower) {
            minPower = maxPower;
        }
        String finalOrderBy = orderBy;
        if(!Stream.of(orderByParamQuery).anyMatch(entry -> entry.containsKey(finalOrderBy))){
            orderQuery = orderByParamQuery.entrySet().iterator().next().getValue();
        }else{
            orderQuery = orderByParamQuery.get(orderBy);
        }
        //пересчитать текущую search_page
        searchPage += searchPageIncrement;
        if (searchPage < 1) {
            searchPage = 1;
        }
        try {
            //отправить запрос в ДАО на количество удовлетворяющих записей
            totalProductsFound = productDao.countBySearchParam(brandId, typeId, minPrice, maxPrice, minPower, maxPower, searchWord);
            if (totalProductsFound > 0) {
                totalPages = totalProductsFound / PRODUCTS_ON_PAGE;
                if ((totalProductsFound % PRODUCTS_ON_PAGE) > 0) {
                    totalPages++;
                }
            } else {
                return productQuantityMap;
            }
            if (searchPage > totalPages) {
                searchPage = totalPages;
            }
            //TODO + 1?
            int searchTo = searchPage * PRODUCTS_ON_PAGE;
            int searchFrom = searchTo - PRODUCTS_ON_PAGE;
            productQuantityMap = productDao.findBySearchParam(brandId, typeId, minPrice, maxPrice, minPower, maxPower, searchFrom, searchTo, searchWord, orderQuery);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService error while findProductsByParam. {}", e.getMessage());
            throw new ServiceException(e);
        }
        searchParamMap.put(TOTAL_PAGE, String.valueOf(totalPages));
        searchParamMap.put(TOTAL_PRODUCT_FOUND, String.valueOf(totalProductsFound));
        searchParamMap.put(SEARCH_PAGE, String.valueOf(searchPage));

        return productQuantityMap;
    }

    @Override
    public Product findProductById(String id) throws ServiceException {
        AttributeValidator attributeValidator = AttributeValidatorImpl.getInstance();
        if (!attributeValidator.isIntValid(id)) {
            logger.log(Level.ERROR, "ProductService error while findProductById incorrect id :{}", id);
            throw new ServiceException("ProductService findProductById incorrect id :" + id);
        }
        Optional<Product> optionalProduct;
        BaseDao<Product> dao = new ProductDaoImpl();
        try {
            optionalProduct = dao.findEntityByOneParam(ID, id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService exception while findProductsById. {}", e.getMessage());
            throw new ServiceException(e);
        }
        if (optionalProduct.isEmpty()) {
            logger.log(Level.ERROR, "ProductService error while findProductById.  id :{}", id);
            throw new ServiceException("ProductService findProductById Product was not found by id :" + id);
        }
        return optionalProduct.get();

    }

    @Override
    public Brand findBrandById(String id) throws ServiceException {
        Optional<Brand> optionalBrand;
        BaseDao<Brand> dao = new BrandDaoImpl();
        try {
            optionalBrand = dao.findEntityByOneParam(ID, id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService exception while findBrandById. {}", e.getMessage());
            throw new ServiceException(e);
        }
        if (optionalBrand.isEmpty()) {
            logger.log(Level.ERROR, "ProductService error while findBrandById.  id :{}", id);
            throw new ServiceException("ProductService findBrandById Brand was not found by id :" + id);
        }
        return optionalBrand.get();
    }

    @Override
    public ProductType findProductTypeById(String id) throws ServiceException {
        Optional<ProductType> optionalProductType;
        BaseDao<ProductType> dao = new ProductTypeDaoImpl();
        try {
            optionalProductType = dao.findEntityByOneParam(ID, id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "ProductService exception while findProductTypeById. {}", e.getMessage());
            throw new ServiceException(e);
        }
        if (optionalProductType.isEmpty()) {
            logger.log(Level.ERROR, "ProductService error while findProductTypeById.  id :{}", id);
            throw new ServiceException("ProductService findProductTypeById ProductType was not found by id :" + id);
        }
        return optionalProductType.get();
    }


}
