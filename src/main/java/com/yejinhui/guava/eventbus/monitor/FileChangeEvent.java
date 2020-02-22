package com.yejinhui.guava.eventbus.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 22:22
 */
public class FileChangeEvent {

    private final Path path;

    private final WatchEvent.Kind kind;

    public FileChangeEvent(Path path, WatchEvent.Kind kind) {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind getKind() {
        return kind;
    }
}
