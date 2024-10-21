package net.xbookmark.core.impl;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import net.xbookmark.common.exception.BusinessException;
import net.xbookmark.common.response.StatusCode;
import net.xbookmark.common.util.Constants;
import net.xbookmark.common.util.ImgUtil;
import net.xbookmark.config.NavProperties;
import net.xbookmark.core.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2024/01/18 21:02
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private NavProperties navProperties;

    @Override
    public String getFilePrefilePath(String filePath) {
        if (StringUtils.isBlank(filePath) || !new File(filePath).exists()) {
            return null;
        }
        return Constants.FileKey.FILE_PREVIEW_PATH + filePath;
    }

    @Override
    public String uploadFile(MultipartFile file, String prefix) {
        String name = file.getOriginalFilename();
        String suffix = "";
        if (name.contains(".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }

        String key = String.format("%s/%s/%s%s", navProperties.getSystem().getDirPath(), prefix, IdUtil.fastUUID(), suffix);

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(key));
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new BusinessException(StatusCode.FAILED, "上传失败");
        }

        return key;
    }

    @Override
    public String uploadFile(File file, String prefix) {
        if (null == file || !file.exists()) {
            return null;
        }
        String name = file.getName();
        String suffix = "";
        if (name.contains(".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }

        String key = String.format("%s/%s/%s%s", navProperties.getSystem().getDirPath(), prefix, IdUtil.fastUUID(), suffix);

        try {
            FileUtils.copyFile(file, new File(key));
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new BusinessException(StatusCode.FAILED, "上传失败");
        }

        return key;
    }

    @Override
    public String uploadBase64(String imgBase64, String prefix) {
        if (null == imgBase64) {
            return null;
        }
        File file = ImgUtil.decodeImg(imgBase64);
        return uploadFile(file, prefix);
    }

    @Override
    public String copyFile(String originFile, String prefix) {
        if (StringUtils.isBlank(originFile) || !new File(originFile).exists()) {
            return null;
        }

        return uploadFile(new File(originFile), prefix);
    }
}
