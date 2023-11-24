package dev.josemc.nightcore.files;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import dev.josemc.nightcore.NightCore;
import dev.josemc.nightcore.files.annotations.Comment;
import dev.josemc.nightcore.utils.FileUtil;

import java.io.File;
import java.lang.reflect.Field;

public class NYML {
    private final Class<?> data;
    private final CommentedFileConfig document;

    public NYML(String path, Class<?> data) {
        this.data = data;
        document = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().build();
        if (!FileUtil.create(new File(path))) {
            document.load();
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

    public void initializeOptions() {
        Field[] fields = data.getFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(dev.josemc.nightcore.files.annotations.Path.class)) {
                String path = String.join(".",
                        field.getAnnotation(dev.josemc.nightcore.files.annotations.Path.class).value());
                try {
                    document.set(path, field.get(data));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (field.isAnnotationPresent(Comment.class)) {
                    document.setComment(path, " " + String.join("\n ", field.getAnnotation(Comment.class).value()));
                }
            }
        }
        document.save();
    }

    public void loadToClass() {
        Field[] fields = data.getFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(dev.josemc.nightcore.files.annotations.Path.class)) {
                String path = String.join(".",
                        field.getAnnotation(dev.josemc.nightcore.files.annotations.Path.class).value());
                if (!document.contains(path)) {
                    continue;
                }
                try {
                    field.set(data, document.get(path));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
