package com.ceyi.project.dcjewelry.admin.ueditor;

import com.ceyi.project.dcjewelry.upload.UploadResult;
import com.ceyi.project.dcjewelry.upload.UploadService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pw.wecode.project.framework.utils.StringUtils;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/6.
 */
@Controller("AdminUeditorController")
@RequestMapping("/admin/ueditor/")
public class UeditorController {
    @Value("${resHost}")
    private String resHost;
    @Inject
    private Gson gson;
    @Inject
    private UploadService uploadService;

    @RequestMapping("config")
    @ResponseBody
    public Object config(@RequestParam("action") String action, @RequestParam(value = "img", required = false) MultipartFile file) {
        if ("config".equals(action)) {
            Config config = new Config();
            config.setImageUrlPrefix(resHost);
            return config;
        } else if ("upload".equals(action)) {
            String filename = uploadService.upload(file);
            if (StringUtils.hasText(filename)) {
                return new UploadResult(filename);
            } else {
                return new UploadResult("FAIL", "上传文件失败");
            }
        }

        return "";
    }
}
