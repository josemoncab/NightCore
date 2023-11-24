package dev.josemc.nightcore.files;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import dev.josemc.nightcore.NightCore;
import dev.josemc.nightcore.files.annotations.Comment;
import dev.josemc.nightcore.files.annotations.Version;
import dev.josemc.nightcore.utils.FileUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Load read-only config files
 * */
public class NYML {
    private final Class<?> data;
    private final CommentedFileConfig document;

    private NYML(String path, Class<?> data) {
        this.data = data;
        document = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().build();
        if (!FileUtil.create(new File(path))) {
            document.load();
            checkUpdates();
            loadToClass();
        } else {
            initializeOptions();
        }
    }

    public static NYML loadFrom(Class<?> data) {
        if (!data.isAnnotationPresent(dev.josemc.nightcore.files.annotations.File.class)) {
            throw new RuntimeException();
        }
        File file = new File(NightCore.get().getDataFolder(),
                data.getAnnotation(dev.josemc.nightcore.files.annotations.File.class).value());

        return new NYML(file.getPath(), data);
    }

    public void reload() {
        loadToClass();
    }

    private void initializeOptions() {
        document.clear();
        getValidFields().forEach(field -> {
            String path = String.join(".",
                    field.getAnnotation(dev.josemc.nightcore.files.annotations.Path.class).value());

            if (document.contains(path)) {
                return;
            }

            try {
                document.set(path, field.get(data));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field.isAnnotationPresent(Comment.class)) {
                document.setComment(path, " " + String.join("\n ", field.getAnnotation(Comment.class).value()));
            }
        });
        if (data.isAnnotationPresent(Version.class)) {
            document.set("Version", data.getAnnotation(Version.class).value());
            document.setComment("Version", " Do not touch!\n This is used by the plugin to update the config file");
        } else {
            document.set("Version", 1);
            document.setComment("Version", " Do not touch!\n This is used by the plugin to update the config file");
        }
        document.save();
    }

    private void loadToClass() {
        getValidFields().forEach(field -> {
            String path = String.join(".",
                    field.getAnnotation(dev.josemc.nightcore.files.annotations.Path.class).value());

            try {
                field.set(data, document.get(path));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void checkUpdates() {
        if (!data.isAnnotationPresent(Version.class) || document.getInt("Version") == data.getAnnotation(Version.class).value()) {
            return;
        }

        initializeOptions();
    }

    private List<Field> getValidFields() {
        List<Field> list = new LinkedList<>();
        Field[] fields = data.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(dev.josemc.nightcore.files.annotations.Path.class)) {
                list.add(field);
            }
        }
        return list;
    }

}
