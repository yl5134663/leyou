package com.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageUploadService {
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif");

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUploadService.class);
@Autowired
private FastFileStorageClient fastFileStorageClient;
    /**
     * 文件上传
     * 1.检验文件类型
     * 2.校验文件内容
     *
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        //获取文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法:{}", fileName);
            return null;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件类型不合法:{}", fileName);
                return null;
            }
            //保存到fastDFS服务器
        String s=StringUtils.substringAfterLast(fileName,"." );
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), s, null);
            // file.transferTo(new File("E:\\leyou\\images\\" + fileName));
            //生成url地址返回
            //return "http://image.leyou.com/" + fileName;
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (Exception e) {
            LOGGER.info("服务器内部错误:{}", fileName);
        }
        return null;
    }
}
