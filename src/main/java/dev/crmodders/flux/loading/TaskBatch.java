package dev.crmodders.flux.loading;

import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TaskBatch {

    private final ExecutorService executor;
    private final List<Future<?>> tasks = new ArrayList<>();

    public TaskBatch(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(Runnable task) {
        tasks.add(executor.submit(task));
    }

    public void await() throws InterruptedException, ExecutionException {
        for(Future<?> task : tasks) {
            task.get();
        }
    }

    public void await(ExecutorService submit) {
        submit.submit(() -> {
            try {
                await();
            } catch (InterruptedException | ExecutionException e) {
                Logger.error(e);
            }
        });
    }

}
