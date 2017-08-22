package com.ceyi.project.dcjewelry.search;

import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.utils.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingh on 2017/5/9.
 */
@Component
public class SearchService {
    private static final String CONFIG_HOT_SEARCH = "热搜";
    @Inject
    private Gson gson;
    @Inject
    private ConfigService configService;

    public List<String> get() {
        Config config = configService.get(CONFIG_HOT_SEARCH);
        if (config != null) {
            return gson.fromJson(config.getValue(), new TypeToken<List<String>>() {
            }.getType());
        }
        return new ArrayList<>();
    }

    public String getAsString() {
        List<String> words = this.get();
        return StringUtils.join(words, '\n');
    }

    public void save(final String lines) {
        List<String> words;
        if (lines.indexOf("\r\n") != -1) {
            words = StringUtils.split(lines, "\r\n");
        } else {
            words = StringUtils.split(lines, "\n");
        }
        configService.save(new Config(CONFIG_HOT_SEARCH, gson.toJson(words)));
    }

    public List<String> get(int uid) {
        Config config = configService.get(CONFIG_HOT_SEARCH + ":" + uid);
        if (config != null) {
            return gson.fromJson(config.getValue(), new TypeToken<List<String>>() {
            }.getType());
        }
        return new ArrayList<>();
    }

    public void save(int uid, String word) {
        List<String> words = this.get(uid);
        int index = words.indexOf(word);
        if (index != -1) {
            words.remove(index);
        }
        words.add(0, word);
        configService.save(new Config(CONFIG_HOT_SEARCH + ":" + uid, gson.toJson(words)));
    }
}
