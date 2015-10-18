package net.ahisame.iwato.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AnyoneTaskProcedure<A, B> implements TaskProcedure<A, B> {

	private final ArrayList<TaskProcedure<A, B>> procedures;
	private TaskProcedureResult<B> result;
	private final Random random;

	@SafeVarargs
	public AnyoneTaskProcedure(TaskProcedure<A, B>... procedures) {
		super();
		this.procedures = new ArrayList<TaskProcedure<A, B>>(Arrays.asList(procedures));
		this.result = null;
		this.random = new Random();
	}

	@Override
	public synchronized TaskProcedureResult<B> apply(A argument) {
		if (result != null) {
			return result;
		}
		int index = random.nextInt(procedures.size());
		TaskProcedureResult<B> currentResult = procedures.get(index).apply(argument);
		if (!currentResult.isJust()) {
			return currentResult;
		}
		this.result = currentResult;
		return result;
	}

}
