package org.shawn.plugin.bash.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.shawn.plugin.bash.BashPlugin;

import java.util.List;

import static org.shawn.plugin.Constant.*;

@Mojo(name = STD_BASH)
public class StandardBashPlugin extends BashPlugin {

    @Parameter
    private boolean join;

    public void execute() {

        if (commands == null || commands.size() == 0) {
            return;
        }

        Session session = null;
        try {
            Connection connection = new Connection(ip, port);
            connection.connect();
            boolean oauth = connection.authenticateWithPassword(username, password);

            if (oauth) {
                getLog().error("AUTHENTICATION IS FAILED");
                return;
            }

            if (join) {
                session = connection.openSession();
                String commandJoin = join(commands);
                session.execCommand(commandJoin);
                getLog().info(String.format("BASH command [ %s ] success", commandJoin));

            } else {

                for (String command : commands) {
                    session = connection.openSession();
                    session.execCommand(command);
                    getLog().info(String.format("BASH command [ %s ] success", command));
                    session.close();
                }
            }

        } catch (Exception e) {
            getLog().error("BASH HAPPENED A EXCEPTION", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


    public String join(List<String> commands) {

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < commands.size(); i++) {
            if (i == commands.size() - 1) {
                buffer.append(commands.get(i));
                break;
            }
            buffer.append(commands.get(i)).append(JOIN_CHAR);
        }

        return buffer.toString();
    }


}
