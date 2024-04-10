package dev.crmodders.flux.loading;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class CurrentThreadExecutor extends AbstractExecutorService {

	private final Deque<Runnable> queue = new ConcurrentLinkedDeque<Runnable>();

	public void run(long timeout, TimeUnit unit) {
		long end = System.nanoTime() + unit.toNanos(timeout);
		while(!queue.isEmpty()) {
			if (System.nanoTime() >= end)
				break;
			Runnable task = queue.poll();
			assert task != null;
			task.run();
		}
    }

	@Override
	public void execute(Runnable command) {
		queue.offer(command);
	}

	@Override
	public void shutdown() {
	}

	@Override
	public List<Runnable> shutdownNow() {
		ArrayList<Runnable> list = new ArrayList<>(queue);
		queue.clear();
		return list;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

}
