package com.ceyi.project.dcjewelry.picture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.wecode.project.framework.image.ComposeMeta;
import pw.wecode.project.framework.image.EnumPosProvider;
import pw.wecode.project.framework.image.ImageProcessor;
import pw.wecode.project.framework.image.PosEnum;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingh on 2017/4/6.
 */
public class PictureService {
    private static final Logger logger = LoggerFactory.getLogger(PictureService.class);
    private String path;
    private String dir;
    private ImageProcessor imageProcessor = new ImageProcessor();
    @Inject
    private Dao dao;

    public PictureService(String path, String dir) {
        this.path = path;
        this.dir = dir;
    }

    public Picture get(int id) {
        return dao.get(Picture.class, id);
    }

    public List<Picture> getByIds(String ids) {
        if (!StringUtils.hasText(ids)) {
            return new ArrayList<>();
        }
        return dao.query(Picture.class, "SELECT * FROM pictures WHERE id IN (" + ids + ")", new ParamMap<String, Object>("ids", ids));
    }

    public void deleteByIds(String ids) {
        if (!StringUtils.hasText(ids)) {
            return;
        }
        dao.update("DELETE FROM pictures WHERE id IN (" + ids + ")", new ParamMap<>());
    }

    public void batchCreate(List<Picture> pictures) {
        // 为了主键没有使用批量接口
        for (Picture picture : pictures) {
            dao.create(picture);
        }
    }

    public Result waterMark(List<Picture> pictures, int wmId, int pos) {
        logger.debug("pictures:{} wmId:{} pos:{}", pictures, wmId, pos);
        Watermark watermark = this.getWatermark(wmId);
        if (watermark == null) {
            return new Result(Result.CODE_ERROR, "水印不存在");
        }
        ComposeMeta composeMeta = new ComposeMeta(new File(path + watermark.getUrl()), new EnumPosProvider(getPosEnum(pos)));
        for (Picture picture : pictures) {
            String filename = imageProcessor.compose(new File(path + picture.getSrcUrl()), composeMeta);
            if (!StringUtils.hasText(filename)) {
                return new Result(Result.CODE_ERROR, "添加水印失败");
            }
            picture.setUrl(dir + filename);
        }
        return new Result();
    }

    public void saveWatermark(Watermark watermark) {
        if (watermark.getId() > 0) {
            dao.update(watermark);
        } else {
            dao.create(watermark);
        }
    }

    public void deleteWatermark(int[] ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            dao.delete("DELETE FROM watermark WHERE id = :id", new ParamMap<String, Object>("id", id));
        }
    }

    public Watermark getWatermark(int id) {
        return dao.get(Watermark.class, id);
    }

    public List<Watermark> getAllWatermark() {
        return dao.query(Watermark.class, "SELECT * FROM watermark");
    }

    private PosEnum getPosEnum(int pos) {
        PosEnum[] posEnums = new PosEnum[]{PosEnum.LEFT_TOP, PosEnum.LEFT_MIDDLE, PosEnum.LEFT_BOTTOM,
                PosEnum.MIDDLE_TOP, PosEnum.MIDDLE_MIDDLE, PosEnum.MIDDLE_BOTTOM,
                PosEnum.RIGHT_TOP, PosEnum.RIGHT_MIDDLE, PosEnum.RIGHT_BOTTOM};
        if (pos < 0 || pos > posEnums.length) {
            return PosEnum.LEFT_TOP;
        } else {
            return posEnums[pos];
        }
    }
}
