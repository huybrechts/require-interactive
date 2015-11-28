package org.jenkinsci.plugins.requireinteractive;

import hudson.model.Computer;

/**
 * Created by awpyv on 4/06/2015.
 */
public class NoInteractiveDisplayException extends RuntimeException {

    public NoInteractiveDisplayException(Computer c) {
        super("Blocking launch for " + c.getName() + " since an interactive display is required but not available");
    }
}
