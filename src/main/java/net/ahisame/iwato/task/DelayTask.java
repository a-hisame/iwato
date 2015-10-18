package net.ahisame.iwato.task;

import java.util.Date;

import net.ahisame.iwato.callback.Callbacks;
import net.ahisame.iwato.config.DelayConfiguration;
import net.ahisame.iwato.function.TaskProcedure;
import net.ahisame.iwato.function.Unit;

public final class DelayTask<R> {
	private final String taskId;
	private final Date created;
	private final TaskProcedure<Unit, R> procedure;
	private final DelayConfiguration delayConfig;
	private final Callbacks<R> callbacks;

	DelayTask(String taskId, Date created, TaskProcedure<Unit, R> procedure, DelayConfiguration delayConfig,
			Callbacks<R> callbacks) {
		super();
		this.taskId = taskId;
		this.created = created;
		this.procedure = procedure;
		this.delayConfig = delayConfig;
		this.callbacks = callbacks;
	}

	public String getTaskId() {
		return taskId;
	}

	public TaskProcedure<Unit, R> getProcedure() {
		return procedure;
	}

	public DelayConfiguration getDelayConfig() {
		return delayConfig;
	}

	public Callbacks<R> getCallbacks() {
		return callbacks;
	}

	@Override
	public String toString() {
		return "DelayTask [taskId=" + taskId + ", created=" + created + "]";
	}

}
