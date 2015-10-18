package net.ahisame.iwato;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ahisame.iwato.task.DelayTask;

/**
 * 
 * @author a-hisame
 *
 */
public final class IwatoManager {

	private static Logger logger = LoggerFactory.getLogger(IwatoManager.class);

	private final ScheduledThreadPoolExecutor executor;

	public IwatoManager() {
		this(new ScheduledThreadPoolExecutor(2));
	}

	public IwatoManager(ScheduledThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public <R> void addDelayTask(DelayTask<R> task) {
		if (this.executor.isShutdown()) {
			logger.warn("Cannot add task " + task.getTaskId() + " because executor is shutdown already");
			return;
		}
		Retriable<R> executionTask = new Retriable<R>(executor, task);
		long delay = task.getDelayConfig().getFirstDelayTime().toMilliseconds();
		this.executor.schedule(executionTask, delay, TimeUnit.MILLISECONDS);
		logger.info("Task: " + task.getTaskId() + " is added");
	}

	public void shutdown() {
		this.executor.shutdown();
	}

	public void shutdownNow() {
		this.executor.shutdownNow();
	}

}
