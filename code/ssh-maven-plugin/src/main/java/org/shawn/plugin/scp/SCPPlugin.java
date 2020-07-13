package org.shawn.plugin.scp;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.shawn.plugin.BasePlugin;

import static org.shawn.plugin.Constant.*;

@Mojo(name = SCP)
public class SCPPlugin extends BasePlugin {

    @Parameter
    private String src;
    @Parameter
    private String dest;


    public void execute() {

        try {
            Connection connection = new Connection(ip, port);
            connection.connect();
            boolean oauth = connection.authenticateWithPassword(username, password);

            if (oauth) {

                SCPClient scpClient = connection.createSCPClient();
                scpClient.put(src, dest);
                getLog().info("SCP is SUCCESS");
            } else {
                getLog().error("AUTHENTICATION IS FAILED");
            }


        } catch (Exception e) {
            getLog().error("SCP HAPPENED A EXCEPTION", e);
        }

    }
}
