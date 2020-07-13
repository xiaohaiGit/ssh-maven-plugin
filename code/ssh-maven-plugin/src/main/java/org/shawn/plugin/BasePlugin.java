package org.shawn.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class BasePlugin extends AbstractMojo {

    @Parameter
    protected String ip;
    @Parameter
    protected Integer port;
    @Parameter
    protected String username;
    @Parameter
    protected String password;

}
