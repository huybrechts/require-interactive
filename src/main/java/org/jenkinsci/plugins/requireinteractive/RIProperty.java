package org.jenkinsci.plugins.requireinteractive;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class RIProperty extends NodeProperty<Node> {

    @DataBoundConstructor
    public RIProperty() {
    }

    @Extension
    public static class DescriptorImpl extends NodePropertyDescriptor {

        @Override
        public String getDisplayName() {
            return "Require interactive display";
        }

    }

}
