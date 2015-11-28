package org.jenkinsci.plugins.requireinteractive;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.Computer;
import hudson.model.TaskListener;
import hudson.remoting.Channel;
import hudson.slaves.ComputerListener;

import java.io.IOException;

/**
 * Created by awpyv on 4/06/2015.
 */
@Extension
public class RIComputerListener extends ComputerListener {

    @Override
    public void preOnline(Computer c, Channel channel, FilePath root, TaskListener listener) throws IOException, InterruptedException {

        RIProperty p = c.getNode().getNodeProperties().get(RIProperty.class);

        if (p != null) {
            Boolean ia = channel.call(new IsInteractiveCallable(listener));
            if (ia == null || !ia.booleanValue()) {
                listener.error("Aborted launch since this slaves requires an interactive display, and it is not available");
                channel.close(new NoInteractiveDisplayException(c));
                throw new NoInteractiveDisplayException(c);
            }
        }
    }


}
