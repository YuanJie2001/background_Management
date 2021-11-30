package com.vector.manager.sys.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.vector.manager.core.common.Constant;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.upload.QiniuUpload;
import com.vector.manager.core.utils.upload.UploadConfig;
import com.vector.manager.core.utils.upload.UploadResult;
import com.vector.manager.sys.entity.Parameter;
import com.vector.manager.sys.service.IImageService;
import com.vector.manager.sys.service.IParameterService;

import java.io.IOException;

@Api(value = "文件上传", tags = "文件上传接口")
@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private IParameterService parameterService;
    @Autowired
    private IImageService iImageService;

    @ApiOperation(value = "图片上传")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.getSize() == 0) {
            return Result.fail("无效的参数");
        }
        Parameter parameter = parameterService.getParameterByName(Constant.QINIU_UPLOAD);
        UploadConfig uploadConfig = JSONUtil.toBean(parameter.getParamValue(), UploadConfig.class);
        try {
            UploadResult uploadResult = QiniuUpload.upload(file.getInputStream(), uploadConfig);
            iImageService.saveImage(uploadResult, file);
            return Result.ok().add(uploadResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

}
