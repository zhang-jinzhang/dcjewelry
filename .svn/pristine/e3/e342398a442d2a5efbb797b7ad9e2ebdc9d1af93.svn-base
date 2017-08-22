package com.ceyi.project.dcjewelry.admin.picture;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.picture.PictureService;
import com.ceyi.project.dcjewelry.picture.Watermark;
import com.ceyi.project.dcjewelry.upload.UploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lingh on 2017/4/7.
 */
@Controller("AdminWatermarkController")
@RequestMapping("/admin/wm/")
public class WatermarkController {
    @Inject
    private PictureService pictureService;
    @Inject
    private UploadService uploadService;

    @RequestMapping("index")
    public String index() {
        return "admin/wm/index";
    }

    @RequestMapping("query")
    @ResponseBody
    public DatatablesPage<Watermark> queryCategory() {
        Page page = new Page(1, 1000);
        List<Watermark> watermarkList = pictureService.getAllWatermark();
        page.setRecordCount(watermarkList.size());
        page.setData(watermarkList);
        return new DatatablesPage<>(page);
    }

    @RequestMapping("create")
    public String create() {
        return "/admin/wm/create";
    }

    @ResponseBody
    @RequestMapping("save")
    public Result save(Watermark watermark, @RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(Result.CODE_ERROR, "请上传水印图片");
        }
        String pic = uploadService.upload(file);
        if (!StringUtils.hasText(pic)) {
            return new Result(Result.CODE_ERROR, "上传文件失败");
        }
        watermark.setUrl(pic);
        pictureService.saveWatermark(watermark);
        return new Result();
    }

    @ResponseBody
    @RequestMapping("delete")
    public Result delete(@RequestParam("ids[]") int[] ids) {
        pictureService.deleteWatermark(ids);
        return new Result();
    }
}
