package com.ceyi.project.dcjewelry.admin.user;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lingh on 2017/5/7.
 */
@Controller
@RequestMapping("/admin/level/")
public class LevelController {
    @Inject
    private LevelService levelService;

    @RequestMapping("index")
    public String index() {
        return "admin/level/index";
    }

    @RequestMapping("query")
    @ResponseBody
    public DatatablesPage<Level> query() {
        List<Level> levels = levelService.get();
        return new DatatablesPage(levels.size(), levels);
    }

    @RequestMapping("create")
    public String create() {
        return "/admin/level/create";
    }

    @RequestMapping("save")
    @ResponseBody
    public Result save(Level level) {
        levelService.add(level);
        return new Result();
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(@RequestParam("level") int level) {
        levelService.delete(level);
        return new Result();
    }
}
