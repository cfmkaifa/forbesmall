package net.mall.service.impl;

import com.alibaba.fastjson.JSON;
import net.mall.Filter;
import net.mall.Setting;
import net.mall.dao.ProImportQueueDao;
import net.mall.entity.*;
import net.mall.service.*;
import net.mall.util.ConvertUtils;
import net.mall.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProImportQueueServiceImpl extends BaseServiceImpl<ProImportQueue, Long> implements ProImportQueueService {


    @Autowired
    ProImportQueueDao  proImportQueueDao;
    @Autowired
    ProductService  productService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    SpecificationService specificationService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductImageService productImageService;
    private String uploadDir;

    @PostConstruct
    private  void initLoad(){
        Setting setting = SystemUtils.getSetting();
        uploadDir = setting.getUploadDir();
    }



    /***商品定时导入任务
     */
    @Transactional
    public void  proImportTask(){
        try {

            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("inStorage",Filter.Operator.EQ,0));
            List<ProImportQueue> proImportQueues = proImportQueueDao.findList(0,100,filters,null);
            List<Filter> tfilters = new ArrayList<Filter>();
            List<Filter> specfilters = new ArrayList<Filter>();
            if(ConvertUtils.isNotEmpty(proImportQueues)){
                proImportQueues.forEach(proImportQueue -> {
                    Store store = storeService.find(proImportQueue.getStoreId());
                    // 清除数组
                    tfilters.clear();
                    String categoryName = proImportQueue.getCategoryName();
                    tfilters.add(new Filter("name",Filter.Operator.EQ,categoryName.trim()));
                    List<ProductCategory> productCategorys = productCategoryService.findList(0,1,tfilters,null);
                    if(ConvertUtils.isNotEmpty(productCategorys)){
                        ProductCategory productCategory = productCategorys.get(0);
                        Product  product = new Product();
                        // 图片处理
                        List<ProductImage> productImages = new ArrayList<>();
                        String productImgs = proImportQueue.getProductImgs();
                        if(ConvertUtils.isNotEmpty(productImgs)){
                           String[]  proImgArray = productImgs.split(";");
                            for (String fileStr : proImgArray) {
                                if(fileStr.contains("\\")){
                                    fileStr = fileStr.replaceAll("\\\\","/");
                                }
                                String filePath = uploadDir +"/"+ fileStr;
                                System.out.println("=======filePath======="+filePath);
                                File tempFile = new File(filePath);
                                if(tempFile.exists()){
                                    ProductImage productImage =  productImageService.generate(tempFile);
                                    productImages.add(productImage);
                                    System.out.println("=================="+ JSON.toJSONString(productImage));
                                }
                            }
                        }
                        product.setProductImages(productImages);
                        product.setName(proImportQueue.getProName());
                        product.setProductCategory(productCategory);
                        product.setPrice(proImportQueue.getPrice());
                        product.setUnit(proImportQueue.getUnit());
                        product.setWeight(proImportQueue.getWeight());
                        product.setType(Product.Type.GENERAL);
                        product.setIsMarketable(false);
                        product.setGroup(false);
                        product.setPurch(false);
                        product.setIsList(false);
                        product.setIsDelivery(false);
                        product.setIsTop(false);
                        product.setStore(store);
                        product.setIntroduction(proImportQueue.getIntroduction());
                        String productParameters = proImportQueue.getProductParameters();
                        if(ConvertUtils.isNotEmpty(productParameters)){
                            // 商品参数值
                            List<ParameterValue> parameterValues = product.getParameterValues();
                            ParameterValue parameterValue = new ParameterValue();
                            parameterValue.setGroup("商品参数分组");
                            List<ParameterValue.Entry> entries = parameterValue.getEntries();
                            String[]  proParameters =  productParameters.split(";");
                            Arrays.asList(proParameters).forEach(proParameter -> {
                               String[] protParameters =  proParameter.split(":");
                                ParameterValue.Entry parameterEntry = new ParameterValue.Entry();
                                parameterEntry.setName(protParameters[0]);
                                parameterEntry.setValue(protParameters[1]);
                                entries.add(parameterEntry);
                            });
                            parameterValues.add(parameterValue);
                        }
                        String productSpecs = proImportQueue.getProductSpecs();
                        if(ConvertUtils.isNotEmpty(productSpecs)){
                            String[] productSpecArray = productSpecs.split("#");
                            String productSpec = productSpecArray[0];
                            String specPrice = productSpecArray[1];
                            // SKU
                            Sku  sku = new Sku();
                            List<SpecificationItem> specificationItems = new ArrayList<>();
                            List<SpecificationValue> specificationValues = new ArrayList<>();
                            String[] specArray = productSpec.split(";");
                            for (String spec : specArray) {
                                SpecificationItem specificationItem = new SpecificationItem();
                                SpecificationValue specificationValue = new SpecificationValue();
                                String[] spect = spec.split(":");
                                String specName = spect[0];
                                String specVal = spect[1];
                                specificationItem.setName(specName);
                                specfilters.clear();
                                specfilters.add(new Filter("productCategory", Filter.Operator.EQ,productCategory));
                                specfilters.add(new Filter("name", Filter.Operator.EQ,specName));
                                List<Specification> specifications = specificationService.findList(0,1,specfilters,null);
                                SpecificationItem.Entry specEntry = new SpecificationItem.Entry();
                                if(ConvertUtils.isNotEmpty(specifications)){
                                    Specification specification = specifications.get(0);
                                    List<String>  options = specification.getOptions();
                                    if(!options.contains(specVal)){
                                        options.add(specVal);
                                        specificationService.update(specification);
                                    }
                                    specEntry.setId(Integer.valueOf(specification.getId().toString()));
                                    specificationValue.setId(Integer.valueOf(specification.getId().toString()));
                                } else {
                                    Specification specification = new Specification();
                                    specification.setSample(Specification.Sample.NO);
                                    specification.setName(specName);
                                    List<String>  options = new ArrayList<String>();
                                    options.add(specVal);
                                    specification.setOptions(options);
                                    specification.setProductCategory(productCategory);
                                    specificationService.save(specification);
                                    specEntry.setId(Integer.valueOf(specification.getId().toString()));
                                    specificationValue.setId(Integer.valueOf(specification.getId().toString()));
                                }
                                specEntry.setIsSelected(true);
                                specEntry.setValue(specVal);
                                specificationItem.getEntries().add(specEntry);
                                specificationItems.add(specificationItem);
                                specificationValue.setValue(specVal);
                                specificationValues.add(specificationValue);
                            }
                            product.setSpecificationItems(specificationItems);
                            sku.setGroupPrice(BigDecimal.ZERO);
                            sku.setGroup(true);
                            sku.setStock(100);
                            sku.setIsDefault(true);
                            sku.setTotalUnit(new BigDecimal("220"));
                            sku.setUnit(proImportQueue.getUnit());
                            if(specPrice.contains("价格")){
                                sku.setPrice(new BigDecimal(specPrice.split(":")[1]));
                            }
                            sku.setMaxCommission(BigDecimal.ZERO);
                            sku.setSpecificationValues(specificationValues);
                            List<Sku> skus = new ArrayList<Sku>();
                            skus.add(sku);
                            productService.create(product,skus);
                        }
                    }
                    // 新建商品
                    proImportQueue.setInStorage(1);
                    proImportQueueDao.merge(proImportQueue);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
