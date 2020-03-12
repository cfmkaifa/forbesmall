/*
 *
 *
 *
 *
 */
package net.mall.plugin;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import net.mall.Setting;
import net.mall.util.SystemUtils;

/**
 * Plugin - 本地文件存储
 *
 * @author huanghy
 * @version 6.1
 */
@Component("localStoragePlugin")
public class LocalStoragePlugin extends StoragePlugin {

    @Inject
    private ServletContext servletContext;

    @Override
    public String getName() {
        return "本地文件存储";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "";
    }

    @Override
    public String getSiteUrl() {
        return "";
    }

    @Override
    public String getInstallUrl() {
        return null;
    }

    @Override
    public String getUninstallUrl() {
        return null;
    }

    @Override
    public String getSettingUrl() {
        return "/admin/plugin/local_storage/setting";
    }

    @Override
    public void upload(String fileDir, String path, File file, String contentType) {
        File destFile = new File(fileDir, path);
        try {
            FileUtils.moveFile(file, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String getUrl(String path) {
        Setting setting = SystemUtils.getSetting();
        return setting.getFileUrl() + path;
    }

}