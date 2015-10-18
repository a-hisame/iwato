package net.ahisame.iwato.callback;

import net.ahisame.iwato.function.TaskProcedureResult;

public interface SuccessFinalizer<T> {
	void callback(TaskProcedureResult<T> result);
}
