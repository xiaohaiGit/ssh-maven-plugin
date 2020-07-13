# maven-plugin

> 工具目录

* ssh-maven-plugin	为方便开发人员发布到服务器上测试



使用示例：

```xml
<plugin>
    <groupId>org.shawn</groupId>
    <artifactId>ssh-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <ip>10.9.177.70</ip>
        <port>22</port>
        <username>admin</username>
        <password>admin</password>
    </configuration>
    <executions>
        <execution>
            <id>scp file</id>
            <phase>package</phase>
            <goals>
                <goal>scp</goal>
            </goals>
            <configuration>
                <src>${project.build.directory}/${project.build.finalName}.jar</src>
                <dest>/admin/businessApp/SenseCity/services/api-gateway/lib</dest>
            </configuration>
        </execution>
        <execution>
            <id>bash exec</id>
            <phase>package</phase>
            <goals>
                <goal>pty-bash</goal>
            </goals>
            <configuration>
                <log>true</log>
                <commands>
                    <command>cd /admin/businessApp/SenseCity/services/api-gateway/</command>
                    <command>./api-gateway-start.sh</command>
                </commands>
            </configuration>
        </execution>
    </executions>
</plugin>
```

