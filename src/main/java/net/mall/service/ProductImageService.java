/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Service - 商品图片
 *
 * @author huanghy
 * @version 6.1
 */
public interface ProductImageService {

    /**
     * 商品图片过滤
     *
     * @param productImages 商品图片
     */
    void filter(List<ProductImage> productImages);

    /**
     * 生成商品图片
     *
     * @param multipartFile 文件
     * @return 商品图片
     */
    ProductImage generate(MultipartFile multipartFile);


    /***
     * 处理图片
     * @param tempFile
     * @return
     */
    ProductImage generate(File tempFile);
}