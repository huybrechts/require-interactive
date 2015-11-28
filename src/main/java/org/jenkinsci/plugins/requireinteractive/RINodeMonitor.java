package org.jenkinsci.plugins.requireinteractive;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.node_monitors.AbstractAsyncNodeMonitorDescriptor;
import hudson.node_monitors.AbstractNodeMonitorDescriptor;
import hudson.node_monitors.NodeMonitor;
import hudson.remoting.Callable;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

/**
 * Created by awpyv on 5/06/2015.
 */
public class RINodeMonitor extends NodeMonitor {

    @Extension
    public static final AbstractNodeMonitorDescriptor<Boolean> DESCRIPTOR = new AbstractAsyncNodeMonitorDescriptor<Boolean>() {
        @Override
        protected Callable<Boolean,IOException> createCallable(Computer c) {
            Node n = c.getNode();
            if(n==null) return null;
            return new IsInteractiveCallable(null);
        }

        public String getDisplayName() {
            return "Interactive Display";
        }

        @Override
        public NodeMonitor newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new RINodeMonitor();
        }
    };
}
