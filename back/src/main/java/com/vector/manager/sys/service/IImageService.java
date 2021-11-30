package com.vector.manager.sys.service;

import org.springframework.web.multipart.MultipartFile;
import com.vector.manager.core.utils.upload.UploadResult;
import com.vector.manager.sys.entity.Image;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author Acerola
 */

public interface IImageService extends IService<Image> {

    void saveImage(UploadResult uploadResult, MultipartFile file);

}
