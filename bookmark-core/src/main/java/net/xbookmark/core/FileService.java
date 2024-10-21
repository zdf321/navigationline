package net.xbookmark.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: zigui.zdf
 * @description:
 * @date: 2021/01/18 21:02
 */
public interface FileService {

    String getFilePrefilePath(String filePath);

    String uploadFile(MultipartFile file, String prefix);

    String uploadFile(File file, String prefix);

    String uploadBase64(String imgBase64, String prefix);

    String copyFile(String originFile, String prefix);
}
