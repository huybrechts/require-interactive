package org.jenkinsci.plugins.requireinteractive;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.node_monitors.AbstractAsyncNodeMonitorDescriptor;
import hudson.node_monitors.AbstractNodeMonitorDescriptor;
import hudson.node_monitors.NodeMonitor;
import hudson.remoting.Callable;
import hudson.slaves.OfflineCause;
import hudson.util.StreamTaskListener;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

/**
 * Created by awpyv on 5/06/2015.
 */
public class RINodeMonitor extends NodeMonitor {

    public static boolean DISABLE = Boolean.getBoolean(RINodeMonitor.class.getName() + ".DISABLE");

    @Extension
    public static final AbstractNodeMonitorDescriptor<Boolean> DESCRIPTOR = new AbstractAsyncNodeMonitorDescriptor<Boolean>() {
        @Override
        protected Callable<Boolean,IOException> createCallable(Computer c) {
            Node n = c.getNode();
            if(n==null) return null;
            return new IsInteractiveCallable(StreamTaskListener.fromStdout());
        }

        @Override
        protected Boolean monitor(Computer c) throws IOException, InterruptedException {
            Boolean result = super.monitor(c);
            if (!DISABLE) {
                RIProperty p = c.getNode().getNodeProperties().get(RIProperty.class);
                if (result != null && p != null) {
                    if (result == Boolean.FALSE && c.isOnline()) {
                        c.setTemporarilyOffline(true, new RIOfflineCause());
                    } else if (result == Boolean.TRUE && c.isOffline() && c.getOfflineCause() instanceof RIOfflineCause) {
                        c.setTemporarilyOffline(false, null);
                    }
                }
            }
            return result;
        }

        public String getDisplayName() {
            return "Interactive Display";
        }

        @Override
        public NodeMonitor newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new RINodeMonitor();
        }
    };

    public static class RIOfflineCause extends OfflineCause {
        @Override
        public String toString() {
            return "No interactive display";
        }
    }


}
