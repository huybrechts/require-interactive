package org.jenkinsci.plugins.requireinteractive;

import hudson.model.TaskListener;
import hudson.remoting.Callable;
import org.jenkinsci.remoting.RoleChecker;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by awpyv on 5/06/2015.
 */
class IsInteractiveCallable implements Callable<Boolean, IOException> {

    TaskListener listener;

    public IsInteractiveCallable(TaskListener listener) {
        this.listener = listener;
    }

    @Override
    public Boolean call() {
        File f = null;
        try {
            if (GraphicsEnvironment.isHeadless()) {
                return false;
            }

            Toolkit t = Toolkit.getDefaultToolkit();
            Rectangle rect = new Rectangle(t.getScreenSize());
            BufferedImage img = new Robot().createScreenCapture(rect);

            final int xmin = img.getMinX();
            final int ymin = img.getMinY();

            final int ymax = ymin + img.getHeight();
            final int xmax = xmin + img.getWidth();

            f = File.createTempFile(getClass().getName().toString(), ".png");
            ImageIO.write(img, "png", f);

            outer:
            for (int i = xmin; i < xmax; i++) {
                for (int j = ymin; j < ymax; j++) {
                    if ((img.getRGB(i, j) & 0x00FFFFFF) != 0) {
                        return true;
                    }
                }
            }

            return false;
        } catch (AWTException e) {
            if (listener != null) {
                e.printStackTrace(listener.getLogger());
            }
            return null;
        } catch (IOException e) {
            if (listener != null) {
                e.printStackTrace(listener.getLogger());
            }
            return null;
        } finally {
            if (f != null) f.delete();
        }
    }

    @Override
    public void checkRoles(RoleChecker checker) throws SecurityException {

    }
}
