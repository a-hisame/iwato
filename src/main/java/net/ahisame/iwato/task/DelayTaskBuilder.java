package net.ahisame.iwato.task;

import java.util.Date;
import java.util.UUID;

import net.ahisame.iwato.callback.Callbacks;
import net.ahisame.iwato.config.DelayConfiguration;
import net.ahisame.iwato.function.AnyoneTaskProcedure;
import net.ahisame.iwato.function.JoinedTaskProcedure;
import net.ahisame.iwato.function.TaskProcedure;
import net.ahisame.iwato.function.Unit;

public class DelayTaskBuilder<T> {

	private final TaskProcedure<Unit, T> procedure;
	private final DelayConfiguration config;

	private DelayTaskBuilder(TaskProcedure<Unit, T> procedure, DelayConfiguration config) {
		this.procedure = procedure;
		this.config = config;
	}

	public static <T> DelayTaskBuilder<T> starts(TaskProcedure<Unit, T> procedure, DelayConfiguration config) {
		return new DelayTaskBuilder<T>(procedure, config);
	}

	public DelayTask<T> build() {
		return build(Callbacks.<T> empty());
	}

	public DelayTask<T> build(Callbacks<T> callbacks) {
		String taskId = UUID.randomUUID().toString();
		Date created = new Date();
		return new DelayTask<T>(taskId, created, procedure, config, callbacks);
	}

	public final <R> DelayTaskBuilder<R> next(TaskProcedure<T, R> procedure) {
		JoinedTaskProcedure<Unit, T, R> joinedProcedure = new JoinedTaskProcedure<Unit, T, R>(this.procedure,
				procedure);
		return new DelayTaskBuilder<R>(joinedProcedure, config);
	}

	@SafeVarargs
	public final <R> DelayTaskBuilder<R> nextAnyone(TaskProcedure<T, R>... procedures) {
		JoinedTaskProcedure<Unit, T, R> joinedProcedure = new JoinedTaskProcedure<Unit, T, R>(this.procedure,
				new AnyoneTaskProcedure<T, R>(procedures));
		return new DelayTaskBuilder<R>(joinedProcedure, config);
	}

}
