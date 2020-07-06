/*
 *
 *
 *
 *
 */
package net.mall.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.mall.model.ResultModel;
import net.mall.util.BusTypeEnum;
import net.mall.util.RestTemplateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.mall.FileType;
import net.mall.Results;
import net.mall.Setting;
import net.mall.service.FileService;
import net.mall.util.SystemUtils;

/**
 * Controller - 文件
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("commonFileController")
@RequestMapping("/common/file")
public class FileController {

    @Inject
    private FileService fileService;
    /***文件存放目录
     * **/
    @SuppressWarnings("unused")
    private String fileDir;

    /***
     * initUploadDir方法慨述:
     *  void
     * @创建人 huanghy
     * @创建时间 2019年12月19日 下午6:05:02
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @PostConstruct
    private void initUploadDir() {
        Setting setting = SystemUtils.getSetting();
        fileDir = setting.getUploadDir();
    }


    /***查询链详情
     * @param fileUrl
     * @param request
     * @return
     */
    @PostMapping("/chain")
    @ResponseBody
    public ResponseEntity<?> chain(String fileUrl, HttpServletRequest request) {
        try {
            String idStr = java.net.URLDecoder.decode(fileUrl,"utf-8");
            ResultModel responseEntity = RestTemplateUtil.reqTemplate(idStr, BusTypeEnum.FILE.getCode());
            if("000000".equals(responseEntity.getResultCode())){
                return Results.status(HttpStatus.OK,responseEntity.getData());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Results.OK;
    }

    /**
     * 上传
     */
    @PostMapping("/upload")
    public ResponseEntity<?> upload(FileType fileType, MultipartFile file) {
        Map<String, Object> data = new HashMap<>();
        if (fileType == null || file == null || file.isEmpty()) {
            return Results.unprocessableEntity("business.order.uploadFile");
        }
        if (!fileService.isValid(fileType, file)) {
            return Results.unprocessableEntity("common.upload.invalid");
        }
        String url = fileService.upload(fileType, file, false);
        if (StringUtils.isEmpty(url)) {
            return Results.unprocessableEntity("common.upload.error");
        }

        data.put("url", url);
        return ResponseEntity.ok(data);
    }
}