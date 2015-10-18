package net.ahisame.iwato.function;

public class JoinedTaskProcedure<A, B, C> implements TaskProcedure<A, C> {

	private final TaskProcedure<A, B> firstProcedure;
	private final TaskProcedure<B, C> secondProcedure;

	private TaskProcedureResult<B> firstResult;
	private TaskProcedureResult<C> secondResult;

	public JoinedTaskProcedure(TaskProcedure<A, B> firstProcedure, TaskProcedure<B, C> secondProcedure) {
		super();
		this.firstProcedure = firstProcedure;
		this.secondProcedure = secondProcedure;
		this.firstResult = null;
		this.secondResult = null;
	}

	@Override
	public synchronized TaskProcedureResult<C> apply(A argument) {
		if (secondResult != null) {
			return secondResult;
		}
		if (firstResult != null) {
			TaskProcedureResult<C> result = secondProcedure.apply(firstResult.getValue());
			if (result.isJust()) {
				secondResult = result;
			}
			return result;
		}
		TaskProcedureResult<B> result1 = firstProcedure.apply(argument);
		if (!result1.isJust()) {
			return result1.convertNothing();
		}
		this.firstResult = result1;

		TaskProcedureResult<C> result2 = secondProcedure.apply(result1.getValue());
		if (!result2.isJust()) {
			return result2;
		}
		this.secondResult = result2;
		return secondResult;
	}

}
