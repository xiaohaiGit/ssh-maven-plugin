package org.shawn.plugin.bash;

import org.apache.maven.plugins.annotations.Parameter;
import org.shawn.plugin.BasePlugin;

import java.util.List;

public abstract class BashPlugin extends BasePlugin {

    @Parameter
    protected List<String> commands;

}
