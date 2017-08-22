package com.ceyi.project.dcjewelry.admin.banner;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.banner.Banner;
import com.ceyi.project.dcjewelry.banner.BannerService;
import com.ceyi.project.dcjewelry.upload.UploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lingh on 2017/4/7.
 */
@Controller("AdminBannerController")
@RequestMapping("/admin/banner/")
public class BannerController {
    @Inject
    private BannerService bannerService;
    @Inject
    private UploadService uploadService;

    @RequestMapping("index")
    public String index() {
        return "admin/banner/index";
    }

    @RequestMapping("query")
    @ResponseBody
    public DatatablesPage<Banner> queryCategory() {
        Page page = new Page(1, 1000);
        List<Banner> bannerList = bannerService.getAll();
        page.setRecordCount(bannerList.size());
        page.setData(bannerList);
        return new DatatablesPage<>(page);
    }

    @RequestMapping("create")
    public ModelAndView create(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("/admin/banner/create");
        if (id != null && id > 0) {
            Banner banner = bannerService.get(id);
            mav.addObject("banner", banner);
        }
        return mav;
    }

    @ResponseBody
    @RequestMapping("save")
    public Result save(Banner banner, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (banner.getId() > 0) {
            Banner old = bannerService.get(banner.getId());
            if (old != null) {
                banner.setCreateTime(old.getCreateTime());
                if (file == null) {
                    banner.setPic(old.getPic());
                }
            }
        }
        String pic = uploadService.upload(file);
        if (!StringUtils.hasText(pic) && !file.isEmpty()) {
            return new Result(Result.CODE_ERROR, "上传文件失败");
        }
        if (StringUtils.hasText(pic)) {
            banner.setPic(pic);
        }
        bannerService.save(banner);
        return new Result();
    }

    @ResponseBody
    @RequestMapping("delete")
    public Result delete(@RequestParam("ids[]") int[] ids) {
        bannerService.delete(ids);
        return new Result();
    }

    @ResponseBody
    @RequestMapping("updateStatus")
    public Result updateStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        Banner banner = bannerService.get(id);
        if (banner == null) {
            return new Result(Result.CODE_ERROR, "banner不存在");
        }
        banner.setStatus(status);
        bannerService.save(banner);
        return new Result();
    }
}
