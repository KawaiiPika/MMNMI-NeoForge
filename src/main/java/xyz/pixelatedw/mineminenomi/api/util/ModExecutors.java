package xyz.pixelatedw.mineminenomi.api.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModExecutors {
    public static final ExecutorService VIRTUAL_THREAD_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
}
