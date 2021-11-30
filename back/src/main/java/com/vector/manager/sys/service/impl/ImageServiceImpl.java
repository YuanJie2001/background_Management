package com.vector.manager.sys.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import com.vector.manager.core.utils.upload.UploadResult;
import com.vector.manager.sys.entity.Image;
import com.vector.manager.sys.dao.ImageDao;
import com.vector.manager.sys.service.IImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageDao, Image> implements IImageService {

    @Async
    @Override
    public void saveImage(UploadResult uploadResult, MultipartFile file) {
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setFileUrl(uploadResult.getUrl());
        image.setFileSize(file.getSize());
        this.save(image);
    }

}
