/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import net.mall.FileType;
import net.mall.Setting;
import net.mall.plugin.StoragePlugin;
import net.mall.service.FileService;
import net.mall.service.PluginService;
import net.mall.util.SystemUtils;

/**
 * Service - 文件
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class FileServiceImpl implements FileService {

	@Inject
	private ServletContext servletContext;
	@Inject
	private TaskExecutor taskExecutor;
	@Inject
	private PluginService pluginService;

	/**
	 * 添加文件上传任务
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void addUploadTask(final String uploadDir,final StoragePlugin storagePlugin, final String path, final File file, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				upload(uploadDir,storagePlugin, path, file, contentType);
			}
		});
	}

	/**
	 * 上传文件
	 * 
	 * @param storagePlugin
	 *            存储插件
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	private void upload(String uploadDir,StoragePlugin storagePlugin, String path, File file, String contentType) {
		Assert.notNull(storagePlugin, "[Assertion failed] - storagePlugin is required; it must not be null");
		Assert.hasText(path, "[Assertion failed] - path must have text; it must not be null, empty, or blank");
		Assert.notNull(file, "[Assertion failed] - file is required; it must not be null");
		Assert.hasText(contentType, "[Assertion failed] - contentType must have text; it must not be null, empty, or blank");
		try {
			storagePlugin.upload(uploadDir,path, file, contentType);
		} finally {
			FileUtils.deleteQuietly(file);
		}
	}

	@Override
	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType, "[Assertion failed] - fileType is required; it must not be null");
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");
		Setting setting = SystemUtils.getSetting();
		if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
			return false;
		}
		String[] uploadExtensions;
		switch (fileType) {
		case MEDIA:
			uploadExtensions = setting.getUploadMediaExtensions();
			break;
		case FILE:
			uploadExtensions = setting.getUploadFileExtensions();
			break;
		default:
			uploadExtensions = setting.getUploadImageExtensions();
			break;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			return FilenameUtils.isExtension(StringUtils.lowerCase(multipartFile.getOriginalFilename()), uploadExtensions);
		}
		return false;
	}

	@Override
	public String upload(FileType fileType, MultipartFile multipartFile, boolean async) {
		Assert.notNull(fileType, "[Assertion failed] - fileType is required; it must not be null");
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");
		Setting setting = SystemUtils.getSetting();
		String uploadDir = setting.getUploadDir();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", String.valueOf(UUID.randomUUID()));
		switch (fileType) {
		case MEDIA:
			uploadPath = setting.resolveMediaUploadPath(model);
			break;
		case FILE:
			uploadPath = setting.resolveFileUploadPath(model);
			break;
		default:
			uploadPath = setting.resolveImageUploadPath(model);
			break;
		}
		try {
			String destPath = uploadPath + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				String contentType = multipartFile.getContentType();
				if (async) {
					addUploadTask(uploadDir,storagePlugin, destPath, tempFile, contentType);
				} else {
					upload(uploadDir,storagePlugin, destPath, tempFile, contentType);
				}
				return storagePlugin.getUrl(destPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String upload(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType, "[Assertion failed] - fileType is required; it must not be null");
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");

		return upload(fileType, multipartFile, true);
	}

	@Override
	public String uploadLocal(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType, "[Assertion failed] - fileType is required; it must not be null");
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", String.valueOf(UUID.randomUUID()));
		switch (fileType) {
		case MEDIA:
			uploadPath = setting.resolveMediaUploadPath(model);
			break;
		case FILE:
			uploadPath = setting.resolveFileUploadPath(model);
			break;
		default:
			uploadPath = setting.resolveImageUploadPath(model);
			break;
		}
		try {
			String destPath = uploadPath + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath("/"), destPath);
			File destDir = destFile.getParentFile();
			if (destDir != null) {
				destDir.mkdirs();
			}
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}