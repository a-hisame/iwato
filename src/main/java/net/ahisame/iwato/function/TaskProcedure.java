package net.ahisame.iwato.function;

public interface TaskProcedure<A, R> {
	TaskProcedureResult<R> apply(A argument);
}
