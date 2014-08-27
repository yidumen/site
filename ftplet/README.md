#FTP分发模块
FTP分发模块是附属在`Apache Ftp Server`上的一个`ftplet`。它实现了如下功能：

 1. 一个自定义命令`DEPLOY`。作用是运行bat合并文件并移动到相应的本地文件夹。
 2. 把文件上传到OSS中，这个功能是以一个`ftplet`的形式提供。
 3. 访问由`cms`模块提供的REST Web Service更新视频信息，这也是以`ftplet`的形式提供。
 
可以看出来，除了自定义命令以外，其它的功能都是以`ftplet`的形式提供的，以后如果需要添加新的功能也应该采取这种方式。好处是可以通过配置启用或停用相应功能。比如说如果以后不再使用OSS了，那么可以在配置文件中删除相关配置就行了。

##如何使用
这个模块的运行依赖于`Apache Ftp Server`，应该先下载一个`Apache Ftp Server`。

官方网站：http://mina.apache.org/ftpserver-project/

下载解压之后，按照官方文档的说明进行配置基本功能，这里不再赘述。只说明如果配置使用模块的功能。

###使用自定义命令
xml文件中加入如下配置：

```xml
<commands use-default="false">
	<command name="DEPLOY">
		<beans:bean class="com.yidumen.cms.ftpserver.command.Deploy">
			<beans:property name="workdir" value="path/to/batdir" />
			<beans:property name="batName" value="bat_name.bat" />
			<beans:property name="parent" value="path/to/parent/video_dir" />
		</beans:bean>
	</command>
</commands>
```
