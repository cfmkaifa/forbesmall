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

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@PostConstruct
	private void initUploadDir(){
		Setting setting = SystemUtils.getSetting();
		fileDir = setting.getUploadDir();
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