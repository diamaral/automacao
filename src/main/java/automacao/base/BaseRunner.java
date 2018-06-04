package automacao.base;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class BaseRunner extends BlockJUnit4ClassRunner {

	public BaseRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	public void run(RunNotifier notifier) {
		notifier.addListener(new JUnitListener());
		notifier.fireTestRunStarted(getDescription());
		super.run(notifier);
	}
}
