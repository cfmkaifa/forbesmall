/*
 *
 *
 *
 *
 */
package net.mall.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.TypeUtil;
import com.alibaba.excel.write.exception.ExcelGenerateException;
import net.mall.Setting;
import net.mall.entity.*;
import net.mall.excel.ProImportQueueExcelMode;
import net.mall.service.*;
import net.mall.util.SystemUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.Results;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller - 商品
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

    @Inject
    private ProductService productService;
    @Inject
    private ProductCategoryService productCategoryService;
    @Inject
    private BrandService brandService;
    @Inject
    private ProductTagService productTagService;
    @Inject
    private StoreService storeService;
    @Inject
    ProImportQueueService proImportQueueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Product.Type type, Long productCategoryId, Long brandId, Long productTagId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        Brand brand = brandService.find(brandId);
        ProductTag productTag = productTagService.find(productTagId);

        model.addAttribute("types", Product.Type.values());
        model.addAttribute("productCategoryTree", productCategoryService.findTree());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("stores", storeService.findAll());
        model.addAttribute("productTags", productTagService.findAll());
        model.addAttribute("type", type);
        model.addAttribute("productCategoryId", productCategoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("productTagId", productTagId);
        model.addAttribute("isMarketable", isMarketable);
        model.addAttribute("isList", isList);
        model.addAttribute("isTop", isTop);
        model.addAttribute("isActive", isActive);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("isStockAlert", isStockAlert);
        model.addAttribute("page", productService.findPage(type, 0,false, null, null,null,productCategory, null, brand, null, productTag, null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
        return "admin/product/list";
    }

    /****
     * 商品导入
     * @param productFilePath
     * @param request
     * @param storeId
     * @return
     */
    @PostMapping("/import-product")
    public ResponseEntity<?> importProduct(@RequestParam(value = "productFilePath",required = true)String productFilePath,
                                           @RequestParam(value = "storeId",required = true) Long storeId,
                                           HttpServletRequest request) {
        Setting setting = SystemUtils.getSetting();
        String uploadDir = setting.getUploadDir();
        String fileUrl = setting.getFileUrl();
        if(productFilePath.startsWith(fileUrl)){
            productFilePath = productFilePath.substring(fileUrl.lastIndexOf(fileUrl)+fileUrl.length(),productFilePath.length());
        }
        File file = new File(uploadDir,productFilePath);
        if(file.exists()){
            try {
                AnalysisEventListener<List<String>> analysisEventListener =  new AnalysisEventListener<List<String>>(){
                    @Override
                    public void invoke(List<String> row, AnalysisContext analysisContext) {
                        analysisContext.buildExcelHeadProperty(ProImportQueueExcelMode.class,row);
                        if (analysisContext.getExcelHeadProperty() != null && analysisContext.getExcelHeadProperty().getHeadClazz() != null) {
                            ProImportQueueExcelMode resultModel = (ProImportQueueExcelMode) this.buildUserModel(analysisContext, (List)row);
                            /***批量导入
                             * **/
                            if(!resultModel.getCategoryName().trim().equals("分类名称")){
                                ProImportQueue proImportQueue = new ProImportQueue();
                                proImportQueue.setCategoryName(resultModel.getCategoryName());
                                proImportQueue.setProName(resultModel.getProName());
                                proImportQueue.setProSn(resultModel.getProSn());
                                proImportQueue.setUnit(resultModel.getUnit());
                                proImportQueue.setWeight(Integer.parseInt(resultModel.getWeight()));
                                proImportQueue.setPrice(new BigDecimal(resultModel.getPrice()));
                                proImportQueue.setIntroduction(resultModel.getIntroduction());
                                proImportQueue.setProductImgs(resultModel.getProductImgs());
                                proImportQueue.setProductParameters(resultModel.getProductParameters());
                                proImportQueue.setProductAttributes(resultModel.getProductAttributes());
                                proImportQueue.setProductSpecs(resultModel.getProductSpecs());
                                proImportQueue.setInStorage(0);
                                proImportQueue.setStoreId(storeId);
                                proImportQueueService.save(proImportQueue);
                            }
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                    }


                    private Object buildUserModel(AnalysisContext context, List<String> stringList) {
                        ExcelHeadProperty excelHeadProperty = context.getExcelHeadProperty();
                        Object resultModel;
                        try {
                            resultModel = excelHeadProperty.getHeadClazz().newInstance();
                        } catch (Exception var10) {
                            throw new ExcelGenerateException(var10);
                        }

                        if (excelHeadProperty != null) {
                            for(int i = 0; i < stringList.size(); ++i) {
                                ExcelColumnProperty columnProperty = excelHeadProperty.getExcelColumnProperty(i);
                                if (columnProperty != null) {
                                    Object value = TypeUtil.convert((String)stringList.get(i), columnProperty.getField(), columnProperty.getFormat(), context.use1904WindowDate());
                                    if (value != null) {
                                        try {
                                            org.apache.commons.beanutils.BeanUtils.setProperty(resultModel, columnProperty.getField().getName(), value);
                                        } catch (Exception var9) {
                                            throw new ExcelGenerateException(columnProperty.getField().getName() + " can not set value " + value, var9);
                                        }
                                    }
                                }
                            }
                        }
                        return resultModel;
                    }
                };
                ExcelReader excelReader = new ExcelReader(new FileInputStream(file), ExcelTypeEnum.XLSX,null,analysisEventListener,true);
                excelReader.read();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return Results.OK;
    }
    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Long[] ids) {
        productService.delete(ids);
        return Results.OK;
    }

    /**
     * 上架商品
     */
    @PostMapping("/shelves")
    public ResponseEntity<?> shelves(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                Product product = productService.find(id);
                if (product == null) {
                    return Results.UNPROCESSABLE_ENTITY;
                }
                if (!storeService.productCategoryExists(product.getStore(), product.getProductCategory())) {
                    return Results.unprocessableEntity("admin.product.marketableNotExistCategoryNotAllowed", product.getName());
                }
            }
            productService.shelves(ids);
        }
        return Results.OK;
    }

    /**
     * 下架商品
     */
    @PostMapping("/shelf")
    public ResponseEntity<?> shelf(Long[] ids) {
        productService.shelf(ids);
        return Results.OK;
    }
}