package org.shawn.plugin.bash.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.shawn.plugin.bash.BashPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.shawn.plugin.Constant.*;

@Mojo(name = PTY_BASH)
public class PTYBashPlugin extends BashPlugin {

    @Parameter
    private boolean log;

    @Override
    public void execute() {

        if (commands == null || commands.size() == 0) {
            return;
        }

        Session session = null;

        try {

            //连接，认证
            Connection connection = new Connection(ip, port);
            connection.connect();
            boolean oauth = connection.authenticateWithPassword(username, password);

            //认证失败，退出
            if (!oauth) {
                getLog().error("AUTHENTICATION IS FAILED");
                return;
            }

            //开启会话
            session = connection.openSession();
            session.requestPTY(BASH);
            session.startShell();

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(session.getStdin(), UTF_8));

            //发送命令
            commands.add(EXIT_COMMAND);
            for (String command : commands) {
                out.write(command + LF);
                out.flush();
            }

            //日志打印
            if (log) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(session.getStdout(), UTF_8));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    getLog().info(">>> " + line);
                }
            }

            //成功日志
            getLog().info("BASH COMMAND EXECUTE SUCCESS");

        } catch (Exception e) {
            getLog().error("BASH HAPPENED A EXCEPTION", e);
        } finally {
            //关闭会话
            if (session != null) {
                session.close();
            }
        }

    }


}
