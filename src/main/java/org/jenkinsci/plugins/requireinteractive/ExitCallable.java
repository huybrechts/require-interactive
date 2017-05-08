package org.jenkinsci.plugins.requireinteractive;

import org.jenkinsci.remoting.RoleChecker;

/**
 * Created by AWPYV on 24/02/2016.
 */
public class ExitCallable implements hudson.remoting.Callable<Object, Throwable> {
	@Override
	public Object call() throws Throwable {
		Thread.sleep(5000);
		System.exit(0);
		return null;
	}

	@Override
	public void checkRoles(RoleChecker checker) throws SecurityException {
	}
}
