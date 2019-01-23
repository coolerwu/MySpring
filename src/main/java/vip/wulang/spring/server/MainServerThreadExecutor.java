package vip.wulang.spring.server;

import vip.wulang.spring.exception.MainServerSocketException;
import vip.wulang.spring.exception.NotDefExecutorServiceType;
import vip.wulang.spring.server.request.HttpRequest;

import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;

/**
 * This class is used to manage multiple thread pools.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class MainServerThreadExecutor implements Callable<Boolean> {
    private static final int TYPE = 0;
    private static final int ZERO = 0;
    private static final int TEN = 10;

    private MainServerSocket mainServerSocket;
    private ExecutorService executorService;
    private Deque<HttpRequest> requestDeque = new ArrayDeque<>();
    private volatile boolean shutdown = false;

    private MainServerThreadExecutor(int type, int nThreads, int corePoolSize, int port)
            throws NotDefExecutorServiceType, MainServerSocketException {
        switch (type) {
            case 0:
                executorService = Executors.newFixedThreadPool(nThreads);
                break;
            case 1:
                executorService = Executors.newSingleThreadExecutor();
                break;
            case 2:
                executorService = Executors.newCachedThreadPool();
                break;
            case 3:
                executorService = Executors.newScheduledThreadPool(corePoolSize);
                break;
            default:
                throw new NotDefExecutorServiceType();
        }

        this.mainServerSocket = new MainServerSocket(port);
    }

    private void startRunnable() throws MainServerSocketException {
        while (!shutdown) {
            Socket socket = mainServerSocket.startOnly();
            runByRunnable(new MainServerSocketRunnable(socket));
        }
    }

    private void runByRunnable(Runnable runnable) {
        executorService.submit(runnable);
    }

    @Override
    public Boolean call() throws MainServerSocketException {
        startRunnable();

        return true;
    }

    @Deprecated
    public static MainServerThreadExecutor init(int port)
            throws MainServerSocketException, NotDefExecutorServiceType {
        return new MainServerThreadExecutor(TYPE, TEN, ZERO, port);
    }

    public static EncapsulationResult<Boolean> initAsync(int port)
            throws MainServerSocketException, NotDefExecutorServiceType {
        MainServerThreadExecutor executor = new MainServerThreadExecutor(TYPE, TEN, ZERO, port);
        FutureTask<Boolean> task = new FutureTask<>(executor);
        new Thread(task).start();

        return new EncapsulationResult<>(executor, task);
    }

    public static class EncapsulationResult<V> {
        private MainServerThreadExecutor executor;
        private FutureTask<V> task;

        public EncapsulationResult(MainServerThreadExecutor executor, FutureTask<V> task) {
            this.executor = executor;
            this.task = task;
        }

        public FutureTask<V> getTask() {
            return task;
        }

        public MainServerThreadExecutor getExecutor() {
            return executor;
        }
    }

    private class MainServerSocketRunnable implements Runnable {
        private Socket socket;

        MainServerSocketRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                HttpRequest request = mainServerSocket.startDecodeIS(socket);
                requestDeque.addLast(request);
            } catch (MainServerSocketException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdownNow() {
        shutdown = true;
    }
}
