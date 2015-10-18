package net.ahisame.iwato.callback;

import net.ahisame.iwato.function.TaskResultCode;

public interface FailedFinalizer {
	void callback(TaskResultCode result);
}
