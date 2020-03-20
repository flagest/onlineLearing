package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wu on 2020/2/13 0013
 */
@Api(value = "文件管理接口",description = "文件管理接口提供文件的增,删,改,查")
public interface FileSystemControllerApi {


    @ApiOperation("文件上传接口")
    public UploadFileResult upload(MultipartFile multipartFile,String filetag,String businessKey,String metadata);


}
