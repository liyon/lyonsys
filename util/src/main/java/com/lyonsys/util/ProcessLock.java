package com.lyonsys.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ProcessLock {

    private final FileChannel fileChannel;
    private FileLock lock;

    public ProcessLock(String lockKey) {
        final File lockFile = new File(System.getProperty("java.io.tmpdir"), "process_" + lockKey + ".lock");
        try {
            fileChannel = new RandomAccessFile(lockFile, "rw").getChannel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            release();
            lockFile.delete();
        }));
    }

    public void lock() {
        try {
            lock = fileChannel.lock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void release() {
        if (lock != null) {
            try {
                lock.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
