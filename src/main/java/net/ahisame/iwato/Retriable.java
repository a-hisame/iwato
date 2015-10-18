package net.ahisame.iwato;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ahisame.iwato.callback.Callbacks;
import net.ahisame.iwato.config.WaitTime;
import net.ahisame.iwato.function.TaskProcedureResult;
import net.ahisame.iwato.function.TaskResultCode;
import net.ahisame.iwato.function.Unit;
import net.ahisame.iwato.task.DelayTask;

public final class Retriable<R> implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Retriable.class);

	private final ScheduledThreadPoolExecutor executor;
	private final DelayTask<R> delayTask;
	private final int restRetryCount;

	public Retriable(ScheduledThreadPoolExecutor executor, DelayTask<R> delayTask) {
		this(executor, delayTask, delayTask.getDelayConfig().getMaxRetryCount());
	}

	private Retriable(ScheduledThreadPoolExecutor executor, DelayTask<R> delayTask, int restRetryCount) {
		super();
		this.executor = executor;
		this.delayTask = delayTask;
		this.restRetryCount = restRetryCount;
	}

	@Override
	public void run() {
		logger.debug("Retriable Task Begin: " + delayTask.getTaskId());
		try {
			Callbacks<R> callbacks = this.delayTask.getCallbacks();
			callbacks.getInitializer().initialize();
			TaskProcedureResult<R> result = this.delayTask.getProcedure().apply(Unit.VOID);
			TaskResultCode code = result.getCode();
			switch (code) {
			case SUCCESS:
				logger.debug("Task " + delayTask.getTaskId() + " is succeeded");
				callbacks.getSuccessFinalizer().callback(result);
				break;
			case RETRY:
				logger.debug("Task " + delayTask.getTaskId() + " requires retry");
				retry(code);
				break;
			case FAIL_WITH_RETRY:
				logger.debug("Task " + delayTask.getTaskId() + " is failed and will be retry");
				retry(code);
				break;
			case FAIL_WITH_CANCEL:
				logger.debug("Task " + delayTask.getTaskId() + " is failed and cancel");
				callbacks.getFailedFinalizer().callback(code);
				break;
			default:
				logger.debug("Invalid Result (" + result + ") is returned");
				break;
			}
		} catch (Exception ex) {
			// set FAIL_WITH_RETRY if any exception caused
			logger.warn("Operation failed because unexpected exception is caused", ex);
			retry(TaskResultCode.FAIL_WITH_RETRY);
		}
		logger.debug("Retriable Task End: " + delayTask.getTaskId());
	}

	private void retry(TaskResultCode beforeResult) {
		if (restRetryCount <= 0) {
			logger.warn("Task " + delayTask.getTaskId() + " is cancelled because trial count reaches max ("
					+ delayTask.getDelayConfig().getMaxRetryCount() + ") count");
			this.delayTask.getCallbacks().getFailedFinalizer().callback(beforeResult);
			return;
		}
		Retriable<R> retryTask = new Retriable<R>(executor, delayTask, restRetryCount - 1);
		if (executor.isShutdown()) {
			logger.warn("Task: " + delayTask.getTaskId() + " is cancelled because executor is shutdown already");
			return;
		}
		int trialCount = delayTask.getDelayConfig().getMaxRetryCount() - restRetryCount;
		WaitTime wait = delayTask.getDelayConfig().getRetryWaitTime(trialCount);
		this.executor.schedule(retryTask, wait.toMilliseconds(), TimeUnit.MILLISECONDS);
		logger.debug("Task " + delayTask.getTaskId() + " is re-scheduled, rest retrycount = " + (restRetryCount - 1));
	}
}
