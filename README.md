基于Fensy、freemarker、Lucene开发的JAVA多人轻博客。

具有日志分类、日志归档、日志标签、全文检索、评论管理、在线音乐、多语言国际化、支持多用户操作等功能。

==================================================================================
使用时需要到fensy.properties配置数据库连接。
在loveJ.properties中配置邮件地址。

登录账户：loveJ
登录密码：admin

在线效果体验：http://ketayao.com
==================================================================================

使用说明：
fensy.properties的配置说明：

# DataSource
# 链接池配置
# 连接池类别
jdbc.dataSource=com.mchange.v2.c3p0.ComboPooledDataSource
# 是否显示SQL
jdbc.showSql=true
# 数据库表前缀
jdbc.prefixTableName=lovej_

#MVC
# 根域名
fensy.rootDomain=
# 根域名对应的模板路径
fensy.rootDomainTemplatePath=
# 模板路径
fensy.templatePath=

fensy.otherDomainAndTemplatePath=

# fensy不拦截的地址
fensy.ignoreURIs=
# fensy不拦截的后缀
fensy.ignoreExts=

# Action包路径
fensy.actions=com.ketayao.action
# 页面解析方式
fensy.views=freemarker

#1M
# 上传大小限制
fensy.uploadFileMaxSize=1048576

# 异常处理拦截器
fensy.exceptionHandler=com.ketayao.handler.EmailExceptionHandler
# 自定义拦截器
fensy.interceptorConfig=/**:com.ketayao.interceptor.CustomerInterceptor


loveJ.properties的配置说明：

# 邮件配置（不配置，错误和留言不能正常发送邮件）
# 发送邮件的地址
blog.exception.email.name=
# 发送邮件的密码
blog.exception.email.password=
# 发送邮件服务器地址
blog.exception.email.hotname=
# 接受邮件的地址
blog.exception.email.to=

例如：
#blog.exception.email.name=kuuiio@hotmail.com
#blog.exception.email.password=sfdsfewtr
#blog.exception.email.hotname=smtp.live.com
#blog.exception.email.to=ggyy@gmail.com
