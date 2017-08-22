package com.ceyi.project.dcjewelry;

import com.ceyi.project.dcjewelry.upload.UploadService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/5/7.
 */
@Controller
public class FileController {
    @Inject
    private UploadService uploadService;
    @Inject
    private UserService userService;

    @ResponseBody
    @RequestMapping("/wx/file/upload")
    public Result<String> upload(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "type", required = false) String type) {
        String pic="";
        if("2".equals(type)){
            try{
                pic = uploadService.uploadVideo(file);
            }catch (Exception e){
                return new Result(Result.CODE_ERROR, e.getMessage());
            }
        }else{
            pic = uploadService.upload(file);
        }
        if (!StringUtils.hasText(pic)) {
            return new Result(Result.CODE_ERROR, "上传文件失败");
        }
        if ("bgUrl".equals(type)) {
            User user = Mvc.context().sGet(User.SESSION_KEY);
            user.setBgUrl(pic);
            userService.updateBgUrl(user);
        }
        return new Result<>(pic);
    }
}
