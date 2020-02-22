package com.yejinhui.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 21:29
 */
public class DirectoryTargetMonitor implements TargetMonitor {

    public static final Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    @Override
    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring...", path);

        start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind kind = event.kind();
                    Path path = (Path) event.context();

                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (Exception e) {
                //出现异常，停止
                this.start = false;
            } finally {
                if (watchKey != null) {
                    //必须得reset，否则是不会接收到的
                    //捕获到一次事件不reset后面不会给你
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception {
        LOGGER.info("The directory [{}] monitor will be stop...", path);
        Thread.currentThread().interrupt();//打断，上面的方法会发生中断异常而结束

        this.start = false;
        //释放资源
        this.watchService.close();
        LOGGER.info("The dirctory [{}] monitor will be stop done.", path);
    }

}
