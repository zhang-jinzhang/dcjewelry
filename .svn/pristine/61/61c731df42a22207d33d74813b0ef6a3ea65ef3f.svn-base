package com.ceyi.project.dcjewelry.admin.user;

import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/7.
 */
@Component
public class LevelService {
    private static final String CONFIG_LEVEL = "等级配置";
    @Inject
    private ConfigService configService;
    @Inject
    private Gson gson;

    public List<Level> get() {
        Config config = configService.get(CONFIG_LEVEL);
        if (config == null) {
            return new ArrayList<>();
        }
        List<Level> levels = gson.fromJson(config.getValue(), new TypeToken<List<Level>>() {
        }.getType());
        return levels;
    }

    public void add(Level add) {
        List<Level> levels = this.get();
        List<Level> allLevels = new ArrayList<>(levels.size() + 1);
        boolean added = false;
        for (Level level : levels) {
            if (add.getLevel() < level.getLevel()) {
                allLevels.add(add);
                added = true;
                break;
            } else if (add.getLevel() > level.getLevel()) {
                allLevels.add(level);
            } else {
                level.setPoint(add.getPoint());
                allLevels.add(level);
                added = true;
                break;
            }
        }
        if (!added) {
            allLevels.add(add);
        }
        save(allLevels);
    }

    public void delete(int level) {
        List<Level> levels = this.get();
        Iterator<Level> iterator = levels.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getLevel() == level) {
                iterator.remove();
            }
        }
        save(levels);
    }

    private void save(List<Level> levels) {
        Collections.sort(levels, new Comparator<Level>() {
            @Override
            public int compare(Level o1, Level o2) {
                return o1.getLevel() - o2.getLevel();
            }
        });
        configService.save(new Config(CONFIG_LEVEL, gson.toJson(levels)));
    }
}
