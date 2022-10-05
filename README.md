# Thesis-Editor
# 文档格式排版工具
## 简介
> 本项目计划以排版系统的在线富文本编辑器功能为核心，继而为其周边功能进行拓展。
> 计划: 模板模块、基于Latex导出PDF文档、用户关联（提供毕业论文模板整合，导师在线阅览批阅查看）、讨论区模块等功能

## 目的
> 我们期望能够打造一个集各种模板编辑为一体的平台。
> 该项目基于ΤΕΧ的排版系统的二次改进，基于Latex文本排版系统功能进行二次封装，
> 用户无需了解latex排版系统的复杂使用方式和命令，实现用户在富文本框编写无格式段落，系统即可导出具有排版格式的PDF文档文件。
> 后期也会在此基础上，引入NLP的自动纠错模型，让用户在富文本框进行输入时，系统能自动检索文本内容，并对文本内容中的语法错误进行提示。

## 项目介绍
- 主要以Spring Cloud Alibaba方案的微服务架构。
- 技术栈: spring cloud Alibaba、nacos、spring gateway、openFeign、spring boot、mybatis、 redis、mongodb、mysql

### 服务拆分
#### 服务注册与配置中心： 
> 使用nacos作为服务发现及其配置中心服务；
####统一认证服务：
> 认证模块，用户需要在此登录获取一定权限后方可在后续拥有完整功能
- 使用access_token、refresh_toekn机制，实现token的续签，同时也降低token泄露的风险性 (access_token的短时效性，refresh_toekn的刷新)；
####网关服务：
> 统一服务入口，简单非法请求过滤功能，请求转发功能；
- 统一服务入口，简单非法请求过滤功能； 
- 通过令牌桶算法实现对用户ip的限流操作； 
- 在网关实现了对请求参数的解析和验证，防止请求参数被劫持篡改；
####工具模块：
> 通过工具功能封装
- 将数据库模块、redis、openFeign等进行了简单封装，统一基础配置信息，以便后期其他功能服务的快速引入和使用； 
- 封装认证注解，通过注解的方式控制服务接口是否需要验证身份信息，以及权限的认证，注解的处理选择在拦截器层面处理，判断handlerMethod继而进行前置的业务处理； 
####文档模块：
> 提供文档数据结构存储和Latex语法转换处理
- 使用mongodb作为文档数据的存储方式，采用数据分块的方式对数据保存，提高响应速度； 
- 使用redis作为分布式锁，确保每个文档一个时间段内，只允许一个用户打开，并通过接口轮询策略实 现对文档打开状态的保持； 
- 实现文档数据(tree)扁平和嵌套数据结构的转换算法； 
- 将存储的数据结构转换成符合latex的语法，并写入文件（后期将会借助latex工具生成文档的PDF，尽量保证文档排版的准确性）；
####用户信息管理模块
> 用户信息管理模块，可以在通过此模块对用户账号信息进行修改
- 
####验证码模块
> 统一验证码管理模块
- 邮箱 验证码