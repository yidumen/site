#易度门网站
这是易度门网站的根项目，其下有几个子项目：
 - **DAO模块**是其它项目共用的数据访问模块，包含数据库对应的实体类以及`DAO`（数据访问对象）可以基于`Spring Bean`来使用，也可以通过JavaEE的`CDI`来使用。它最初只实现基于`Spring+Hibernate`的数据库访问，但可能随着环境的变化而增加其它实现。
 - **Web项目**是主要的子项目，甚至这个项目的名称可以用根项目名称替代，可以说其它的子项目都是围绕它展开的。很显然，这个项目所包含的内容就是易度门网站的站点。
 - **CMS项目**就是`Conent Manager System`内容管理系统。顾名思义，这个项目是Web的后台管理。但事实上，它还要负责网站资源的统一管理及维护。
 - **FTP服务器**包含`Apache Ftp Server`的`Ftplet`扩展。由于网站视频需要通过澳门服务器中转，它的作用就是当把视频通过`Apache Ftp Server`上传到澳门服务器后，自动分发到云并更新信息。

当项目创建时，易度门网站已经计划部署到`ACE（Aliyun Cloud Engine，阿里云引擎）`，`ACE`的应用服务器是基于`Tomcat`二次开发的，所以这些项目都是基于`Tomcat`的，首先保证在原生的`Tomcat`中能够正常运行，然后保证能在`ACE`中运行。当然，某些功能需要调用`ACE`容器特有的API，例如邮件，这些功能是无法在普通的容器中运行的。

本项目已经拆分为：
 - https://github.com/yidumen/cms-platform
 - https://github.com/yidumen/web-site
 - https://github.com/yidumen/ftplet
