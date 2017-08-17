# schema配置参考

## 1、<dubbo:application>

应用信息配置：  
配置类：`com.alibaba.dubbo.config.ApplicationConfig`

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| name | string | Y |  | 服务治理 | 当前应用名称，用于注册中心计算应用间依赖关系，注意：消费者和提供者应用名不要一样，此参数不是匹配条件，你当前项目叫什么名字就填什么，和提供者消费者角色无关，比如：kylin应用调用了morgan应用的服务，则kylin项目配成kylin，morgan项目配成morgan，可能kylin也提供其它服务给别人使用，但kylin项目永远配成kylin，这样注册中心将显示kylin依赖于morgan | 1.0.16以上版本 |
| version | string | N |  | 服务治理 | 当前应用的版本 | 2.2.0以上版本 |
| owner | string | N |  | 服务治理 | 应用负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.0.5以上版本 |
| organization | string | N |  | 服务治理 | 组织名称(BU或部门)，用于注册中心区分服务来源，此配置项建议不要使用autoconfig，直接写死在配置中，比如china,intl,itu,crm,asc,dw,aliexpress等 | 2.0.0以上版本 |
| architecture  | string | N |  | 服务治理 | 用于服务分层对应的架构。如，intl、china。不同的架构使用不同的分层。 | 2.0.7以上版本 |
| environment | string | N |  | 服务治理 | 应用环境，如：develop/test/product，不同环境使用不同的缺省值，以及作为只用于开发测试功能的限制条件 | 2.0.0以上版本 |
| compiler | string | N | javassist | 性能优化 | Java字节码编译器，用于动态类的生成，可选：jdk或javassist | 2.1.0以上版本 |
| logger | string | N | slf4j | 性能优化 | 日志输出方式，可选：slf4j,jcl,log4j,jdk | 2.2.0以上版本 |

## 2、<dubbo:registry>

注册中心配置：  
配置类：`com.alibaba.dubbo.config.RegistryConfig`  
说明：如果有多个不同的注册中心，可以声明多个`<dubbo:registry>`标签，并在`<dubbo:service>`或`<dubbo:reference>`的registry属性指定使用的注册中心。

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| id | string | N |  | 配置关联 | 注册中心引用BeanId，可以在<dubbo:service registry="">或<dubbo:reference registry="">中引用此ID | 1.0.16以上版本 |
| address | string | Y |  | 服务发现 | 注册中心服务器地址，如果地址没有端口缺省为9090，同一集群内的多个地址用逗号分隔，如：ip:port,ip:port，不同集群的注册中心，请配置多个<dubbo:registry>标签 | 1.0.16以上版本 |
| protocol | string | N | dubbo | 服务发现 | 注同中心地址协议，支持dubbo, http, local三种协议，分别表示，dubbo地址，http地址，本地注册中心 | 2.0.0以上版本 |
| port | int | N | 9090 | 服务发现 | 注册中心缺省端口，当address没有带端口时使用此端口做为缺省值 | 2.0.0以上版本 |
| username  | string | N |  | 服务治理 | 登录注册中心用户名，如果注册中心不需要验证可不填 | 2.0.0以上版本 |
| password | string | N |  | 服务治理 | 登录注册中心密码，如果注册中心不需要验证可不填 | 2.0.0以上版本 |
| transport | string | N | netty | 性能调优 | 网络传输方式，可选mina,netty | 2.0.0以上版本 |
| timeout | int | N | 5000 | 性能调优 | 注册中心请求超时时间(毫秒) | 2.0.0以上版本 |
| session | int | N | 60000 | 性能调优 | 注册中心会话超时时间(毫秒)，用于检测提供者非正常断线后的脏数据，比如用心跳检测的实现，此时间就是心跳间隔，不同注册中心实现不一样。 | 2.1.0以上版本 |
| file | string | N |  | 服务治理 | 使用文件缓存注册中心地址列表及服务提供者列表，应用重启时将基于此文件恢复，注意：两个注册中心不能使用同一文件存储 | 2.0.0以上版本 |
| wait | int | N | 0 | 性能调优 | 停止时等待通知完成时间(毫秒) | 2.0.0以上版本 |
| check | boolean | N | true | 服务治理 | 注册中心不存在时，是否报错 | 2.0.0以上版本 |
| register | boolean | N | true | 服务治理 | 是否向此注册中心注册服务，如果设为false，将只订阅，不注册 | 2.0.5以上版本 |
| subscribe | boolean | N | true | 服务治理 | 是否向此注册中心订阅服务，如果设为false，将只注册，不订阅 | 2.0.5以上版本 |
| dynamic | boolean | N | true | 服务治理 | 服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用。 | 2.0.5以上版本 |

## 3、<dubbo:protocol>

服务提供者协议配置：  
配置类：`com.alibaba.dubbo.config.ProtocolConfig`  
说明：如果需要支持多协议，可以声明多个`<dubbo:protocol>`标签，并在`<dubbo:service>`中通过protocol属性指定使用的协议。

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| id | string | N | dubbo | 配置关联 | 协议BeanId，可以在<dubbo:service protocol="">中引用此ID，如果ID不填，缺省和name属性值一样，重复则在name后加序号。 | 2.0.5以上版本 |
| name | string | Y | dubbo | 性能调优 | 协议名称 | 2.0.5以上版本 |
| port | int | N | dubbo协议缺省端口为20880，rmi协议缺省端口为1099，http和hessian协议缺省端口为80；如果配置为-1 或者 没有配置port，则会分配一个没有被占用的端口。Dubbo 2.4.0+，分配的端口在协议缺省端口的基础上增长，确保端口段可控。 | 服务发现 | 服务端口 | 2.0.5以上版本 |
| host | string | N | 自动查找本机IP | 服务发现 | -服务主机名，多网卡选择或指定VIP及域名时使用，为空则自动查找本机IP，-建议不要配置，让Dubbo自动获取本机IP | 2.0.5以上版本 |
| threadpool | string | N | fixed | 性能调优 | 线程池类型，可选：fixed/cached | 2.0.5以上版本 |
| threads | int | N | 100 | 性能调优 | 服务线程池大小(固定大小) | 2.0.5以上版本 |
| iothreads | int | N | cpu个数+1 | 性能调优 | io线程池大小(固定大小) | 2.0.5以上版本 |
| accepts | int | N | 0 | 性能调优 | 服务提供方最大可接受连接数 | 2.0.5以上版本 |
| payload | int | N | 88388608(=8M) | 性能调优 | 请求及响应数据包大小限制，单位：字节 | 2.0.5以上版本 |
| codec | string | N | dubbo | 性能调优 | 协议编码方式 | 2.0.5以上版本 |
| serialization | string | N | dubbo协议缺省为hessian2，rmi协议缺省为java，http协议缺省为json | 性能调优 | 协议序列化方式，当协议支持多种序列化方式时使用，比如：dubbo协议的dubbo,hessian2,java,compactedjava，以及http协议的json等 | 2.0.5以上版本 |
| accesslog | string/boolean | N | | 服务治理 | 设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件 | 2.0.5以上版本 |
| path | string | N | | 服务发现 | 提供者上下文路径，为服务path的前缀 | 2.0.5以上版本 |
| transporter | string | N | dubbo协议缺省为netty | 性能调优 | 协议的服务端和客户端实现类型，比如：dubbo协议的mina,netty等，可以分拆为server和client配置 | 2.0.5以上版本 |
| server | string | N | dubbo协议缺省为netty，http协议缺省为servlet | 性能调优 | 协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等 | 2.0.5以上版本 |
| client | string | N | dubbo协议缺省为netty | 性能调优 | 协议的客户端实现类型，比如：dubbo协议的mina,netty等 | 2.0.5以上版本 |
| dispatcher | string | N | dubbo协议缺省为all | 性能调优 | 协议的消息派发方式，用于指定线程模型，比如：dubbo协议的all, direct, message, execution, connection等 | 2.1.0以上版本 |
| queues | int | N | 0 | 性能调优 | 线程池队列大小，当线程池满时，排队等待执行的队列大小，建议不要设置，当线程程池时应立即失败，重试其它服务提供机器，而不是排队，除非有特殊需求。 | 2.0.5以上版本 |
| charset | string | N | UTF-8 | 性能调优 | 序列化编码 | 2.0.5以上版本 |
| buffer | int | N | 8192 | 性能调优 | 网络读写缓冲区大小 | 2.0.5以上版本 |
| heartbeat | int | N | 0 | 性能调优 | 心跳间隔，对于长连接，当物理层断开时，比如拔网线，TCP的FIN消息来不及发送，对方收不到断开事件，此时需要心跳来帮助检查连接是否已断开 | 2.0.5以上版本 |
| telnet | string | N | | 服务治理 | 所支持的telnet命令，多个命令用逗号分隔 | 2.0.5以上版本 |
| register | boolean | N | true | 服务治理 | 该协议的服务是否注册到注册中心 | 2.0.8以上版本 |
| contextpath | string | N | 缺省为空串 | 服务治理 | | 2.0.6以上版本 |

## 4、<dubbo:provider>

服务提供者缺省值配置：  
配置类：`com.alibaba.dubbo.config.ProviderConfig`  
说明：该标签为`<dubbo:service>`和`<dubbo:protocol>`标签的缺省值设置。

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| id | string | N | dubbo | 配置关联 | 协议BeanId，可以在<dubbo:service proivder="">中引用此ID | 1.0.16以上版本 |
| protocol | string | N | dubbo | 性能调优 | 协议名称 | 1.0.16以上版本 |
| host | string | N | 自动查找本机IP | 服务发现 | 服务主机名，多网卡选择或指定VIP及域名时使用，为空则自动查找本机IP，建议不要配置，让Dubbo自动获取本机IP | 1.0.16以上版本 |
| threads | int | N | 100 | 性能调优 | 服务线程池大小(固定大小) | 1.0.16以上版本 |
| payload | int | N | 88388608(=8M) | 性能调优 | 请求及响应数据包大小限制，单位：字节 | 2.0.0以上版本 |
| path | string | N | | 服务发现 | 提供者上下文路径，为服务path的前缀 | 2.0.0以上版本 |
| server | string | N | dubbo协议缺省为netty，http协议缺省为servlet | 性能调优 | 协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等 | 2.0.0以上版本 |
| client | string | N | dubbo协议缺省为netty | 性能调优 | 协议的客户端实现类型，比如：dubbo协议的mina,netty等 | 2.0.0以上版本 |
| codec | string | N | dubbo | 性能调优 | 协议编码方式 | 2.0.0以上版本 |
| serialization | string | N | dubbo协议缺省为hessian2，rmi协议缺省为java，http协议缺省为json | 性能调优 | 协议序列化方式，当协议支持多种序列化方式时使用，比如：dubbo协议的dubbo,hessian2,java,compactedjava，以及http协议的json,xml等 | 2.0.5以上版本 |
| default | boolean | N | false | 配置关联 | 是否为缺省协议，用于多协议 | 1.0.16以上版本 |
| filter | string | N | | 性能调优 | 服务提供方远程调用过程拦截器名称，多个名称用逗号分隔 | 2.0.5以上版本 |
| listener | string | N | | 性能调优 | 服务提供方导出服务监听器名称，多个名称用逗号分隔 | 2.0.5以上版本 |
| threadpool | string | N | fixed | 性能调优 | 线程池类型，可选：fixed/cached | 2.0.5以上版本 |
| accepts | int | N | 0 | 性能调优 | 服务提供者最大可接受连接数 | 2.0.5以上版本 |
| version | string | N | 0.0.0 | 服务发现 | 服务版本，建议使用两位数字版本，如：1.0，通常在接口不兼容时版本号才需要升级 | 2.0.5以上版本 |
| group | string | N | | 服务发现 | 服务分组，当一个接口有多个实现，可以用分组区分 | 2.0.5以上版本 |
| delay | int | N | 0 | 性能调优 | 延迟注册服务时间(毫秒)- ，设为-1时，表示延迟到Spring容器初始化完成时暴露服务 | 2.0.5以上版本 |
| timeout | int | N | 1000 | 性能调优 | 远程服务调用超时时间(毫秒) | 2.0.5以上版本 |
| retries | int | N | 2 | 性能调优 | 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0 | 2.0.5以上版本 |
| connections | int | N | 0 | 性能调优 | 对每个提供者的最大连接数，rmi、http、hessian等短连接协议表示限制连接数，dubbo等长连接协表示建立的长连接个数 | 2.0.5以上版本 |
| loadbalance | string | N | random | 性能调优 | 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用 | 2.0.5以上版本 |
| async | boolean | N | false | 服务治理 | 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 | 2.0.5以上版本 |
| stub | boolean | N | false | 服务治理 | 设为true，表示使用缺省代理类名，即：接口名 + Local后缀。 | 2.0.5以上版本 |
| mock | boolean | N | false | 服务治理 | 设为true，表示使用缺省Mock类名，即：接口名 + Mock后缀。 | 2.0.5以上版本 |
| token | boolean | N | false | 性能调优 | 令牌验证，为空表示不开启，如果为true，表示随机生成动态令牌 | 2.0.5以上版本 |
| registry | string | N | 缺省向所有registry注册 | 配置关联 | 向指定注册中心注册，在多个注册中心时使用，值为<dubbo:registry>的id属性，多个注册中心ID用逗号分隔，如果不想将该服务注册到任何registry，可将值设为N/A | 2.0.5以上版本 |
| dynamic | boolean | N | true | 服务治理 | 服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用。 | 2.0.5以上版本 |
| accesslog | string/boolean | N | false | 服务治理 | 设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件 | 2.0.5以上版本 |
| owner | string | N | | 服务治理 | 服务负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.0.5以上版本 |
| document | string | N | | 服务治理 | 服务文档URL | 2.0.5以上版本 |
| weight | int | N | | 性能调优 | 服务权重 | 2.0.5以上版本 |
| executes | int | N | 0 | 性能调优 | 服务提供者每服务每方法最大可并行执行请求数 | 2.0.5以上版本 |
| actives | int | N | 0 | 性能调优 | 每服务消费者每服务每方法最大并发调用数 | 2.0.5以上版本 |
| proxy | string | N | javassist | 性能调优 | 生成动态代理方式，可选：jdk/javassist | 2.0.5以上版本 |
| cluster | string | N | failover | 性能调优 | 集群方式，可选：failover/failfast/failsafe/failback/forking | 2.0.5以上版本 |
| deprecated | boolean | N | false | 服务治理 | 服务是否过时，如果设为true，消费方引用时将打印服务过时警告error日志 | 2.0.5以上版本 |
| queues | int | N | 0 | 性能调优 | 线程池队列大小，当线程池满时，排队等待执行的队列大小，建议不要设置，当线程程池时应立即失败，重试其它服务提供机器，而不是排队，除非有特殊需求。 | 2.0.5以上版本 |
| charset | string | N | UTF-8 | 性能调优 | 序列化编码 | 2.0.5以上版本 |
| buffer | int | N | 8192 | 性能调优 | 网络读写缓冲区大小 | 2.0.5以上版本 |
| iothreads | int | N | CPU + 1 | 性能调优 | IO线程池，接收网络读写中断，以及序列化和反序列化，不处理业务，业务线程池参见threads配置，此线程池和CPU相关，不建议配置。 | 2.0.5以上版本 |
| telnet | string | N | | 服务治理 | 所支持的telnet命令，多个命令用逗号分隔 | 2.0.5以上版本 |
| contextpath | string | N | 缺省为空串 | 服务治理 | | 2.0.6以上版本 |
| layer | string | N | | 服务治理 | 服务提供者所在的分层。如：biz、dao、intl:web、china:acton。 | 2.0.7以上版本 |

## 5、<dubbo:consumer>

服务消费者缺省值配置：  
配置类：`com.alibaba.dubbo.config.ConsumerConfig`  
说明：该标签为`<dubbo:reference>`标签的缺省值设置。

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| timeout | int | N | 1000 | 性能调优 | 远程服务调用超时时间(毫秒) | 1.0.16以上版本 |
| retries | int | N | 2 | 性能调优 | 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0 | 1.0.16以上版本 |
| loadbalance | string | N | random | 性能调优 | 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用 | 1.0.16以上版本 |
| async | boolean | N | false | 性能调优 | 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 | 2.0.0以上版本 |
| connections | int | N | 100 | 性能调优 | 每个服务对每个提供者的最大连接数，rmi、http、hessian等短连接协议支持此配置，dubbo协议长连接不支持此配置 | 1.0.16以上版本 |
| generic | boolean | N | false | 服务治理 | 是否缺省泛化接口，如果为泛化接口，将返回GenericService | 2.0.0以上版本 |
| check | boolean | N | true | 服务治理 | 启动时检查提供者是否存在，true报错，false忽略 | 1.0.16以上版本 |
| proxy | string | N | javassist | 性能调优 | 生成动态代理方式，可选：jdk/javassist | 2.0.5以上版本 |
| owner | string | N | | 服务治理 | 调用服务负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.0.5以上版本 |
| actives | int | N | 0 | 性能调优 | 每服务消费者每服务每方法最大并发调用数 | 2.0.5以上版本 |
| cluster | string | N | failover | 性能调优 | 集群方式，可选：failover/failfast/failsafe/failback/forking | 2.0.5以上版本 |
| filter | string | N | | 性能调优 | 服务消费方远程调用过程拦截器名称，多个名称用逗号分隔 | 2.0.5以上版本 |
| listener | string | N | | 性能调优 | 服务消费方引用服务监听器名称，多个名称用逗号分隔 | 2.0.5以上版本 |
| registry | string | N | 缺省向所有registry注册 | 配置关联 | 向指定注册中心注册，在多个注册中心时使用，值为<dubbo:registry>的id属性，多个注册中心ID用逗号分隔，如果不想将该服务注册到任何registry，可将值设为N/A | 2.0.5以上版本 |
| layer | string | N | | 服务治理 | 服务调用者所在的分层。如：biz、dao、intl:web、china:acton。 | 2.0.7以上版本 |
| init | boolean | N | false | 性能调优 | 是否在afterPropertiesSet()时饥饿初始化引用，否则等到有人注入或引用该实例时再初始化。 | 2.0.10以上版本 |
| cache | string/boolean | N | | 服务治理 | 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等 | Dubbo2.1.0及其以上版本支持 |
| validation | boolean | N | | 服务治理 | 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验 | Dubbo2.1.0及其以上版本支持 |

## 6、<dubbo:service>

服务提供者暴露服务配置：  
配置类：`com.alibaba.dubbo.config.ServiceConfig`

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ----- | ----- | ----- | ----- | ----- | ------- | ----- |
| interface | class | 必填 | | 服务发现 | 服务接口名 | 1.0.0以上版本 | 
| ref | object | 必填 | | 服务发现 | 服务对象实现引用 | 1.0.0以上版本 | 
| version | string | 可选 | 0.0.0 | 服务发现 | 服务版本，建议使用两位数字版本，如：1.0，通常在接口不兼容时版本号才需要升级 | 1.0.0以上版本 | 
| group | string | 可选 | | 服务发现 | 服务分组，当一个接口有多个实现，可以用分组区分 | 1.0.7以上版本 | 
| path | string | 可选 | 缺省为接口名 | 服务发现 | 服务路径(注意：1.0不支持自定义路径，总是使用接口名，如果有1.0调2.0，配置服务路径可能不兼容) | 1.0.12以上版本 | 
| delay | int | 可选 | 0 | 性能调优 | 延迟注册服务时间(毫秒)，设为-1时，表示延迟到Spring容器初始化完成时暴露服务 | 1.0.14以上版本 | 
| timeout | int | 可选 | 1000 | 性能调优 | 远程服务调用超时时间(毫秒) | 2.0.0以上版本 | 
| retries | int | 可选 | 2 | 性能调优 | 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0 | 2.0.0以上版本 | 
| connections | int | 可选 | 100 | 性能调优 | 对每个提供者的最大连接数，rmi、http、hessian等短连接协议表示限制连接数，dubbo等长连接协表示建立的长连接个数 | 2.0.0以上版本 | 
| loadbalance | string | 可选 | random | 性能调优 | 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用 | 2.0.0以上版本 | 
| async | boolean | 可选 | false | 性能调优 | 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 | 2.0.0以上版本 |
| stub | class/boolean | 可选 | false | 服务治理 | 设为true，表示使用缺省代理类名，即：接口名 + Local后缀，服务接口客户端本地代理类名，用于在客户端执行本地逻辑，如本地缓存等，该本地代理类的构造函数必须允许传入远程代理对象，构造函数如：public XxxServiceLocal(XxxService xxxService) | 2.0.0以上版本 |
| mock | class/boolean | 可选 | false | 服务治理 | 设为true，表示使用缺省Mock类名，即：接口名 + Mock后缀，服务接口调用失败Mock实现类，该Mock类必须有一个无参构造函数，与Local的区别在于，Local总是被执行，而Mock只在出现非业务异常(比如超时，网络异常等)时执行，Local在远程调用之前执行，Mock在远程调用后执行。 | 2.0.0以上版本 |
| token | string/boolean | 可选 | false | 服务治理 | 令牌验证，为空表示不开启，如果为true，表示随机生成动态令牌，否则使用静态令牌，令牌的作用是防止消费者绕过注册中心直接访问，保证注册中心的授权功能有效，如果使用点对点调用，需关闭令牌功能 | 2.0.0以上版本 |
| registry | string | 可选 | 缺省向所有registry注册 | 配置关联 | 向指定注册中心注册，在多个注册中心时使用，值为<dubbo:registry>的id属性，多个注册中心ID用逗号分隔，如果不想将该服务注册到任何registry，可将值设为N/A | 2.0.0以上版本 |
| provider | string | 可选 | 缺使用第一个provider配置 | 配置关联 | 指定provider，值为<dubbo:provider>的id属性 | 2.0.0以上版本 |
| deprecated | boolean | 可选 | false | 服务治理 | 服务是否过时，如果设为true，消费方引用时将打印服务过时警告error日志 | 2.0.5以上版本 |
| dynamic | boolean | 可选 | true | 服务治理 | 服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用。 | 2.0.5以上版本 |
| accesslog | string/boolean | 可选 | false | 服务治理 | 设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件 | 2.0.5以上版本 |
| owner | string | 可选 | | 服务治理 | 服务负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.0.5以上版本 |
| document | string | 可选 | | 服务治理 | 服务文档URL | 2.0.5以上版本 |
| weight | int | 可选 | | 性能调优 | 服务权重 | 2.0.5以上版本 |
| executes | int | 可选 | 0 | 性能调优 | 服务提供者每服务每方法最大可并行执行请求数 | 2.0.5以上版本 |
| actives | int | 可选 | 0 | 性能调优 | 每服务消费者每服务每方法最大并发调用数 | 2.0.5以上版本 |
| proxy | string | 可选 | javassist | 性能调优 | 生成动态代理方式，可选：jdk/javassist | 2.0.5以上版本 |
| cluster | string | 可选 | failover | 性能调优 | 集群方式，可选：failover/failfast/failsafe/failback/forking | 2.0.5以上版本 |
| filter | string | 可选 | default | 性能调优 | 服务提供方远程调用过程拦截器名称，多个名称用逗号分隔 | 2.0.5以上版本 |
| listener | string | 可选 | default | 性能调优 | 服务提供方导出服务监听器名称，多个名称用逗号分隔 |
| protocol | string | 可选 | | 配置关联 | 使用指定的协议暴露服务，在多协议时使用，值为<dubbo:protocol>的id属性，多个协议ID用逗号分隔 | 2.0.5以上版本 |
| layer | string | 可选 | | 服务治理 | 服务提供者所在的分层。如：biz、dao、intl:web、china:acton。 | 2.0.7以上版本 |
| register | boolean | 可选 | true | 服务治理 | 该协议的服务是否注册到注册中心 | 2.0.8以上版本 |

## 7、<dubbo:reference>

服务消费者引用服务配置：  
配置类：`com.alibaba.dubbo.config.ReferenceConfig`

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ----------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| id | string | 必填 | | 配置关联 | 服务引用BeanId | 1.0.0以上版本 | 
| interface | class | 必填 | | 服务发现 | 服务接口名 | 1.0.0以上版本 | 
| version | string | 可选 | | 服务发现 | 服务版本，与服务提供者的版本一致 | 1.0.0以上版本 | 
| group | string | 可选 | | 服务发现 | 服务分组，当一个接口有多个实现，可以用分组区分，必需和服务提供方一致 | 1.0.7以上版本 | 
| timeout | long | 可选 | 缺省使用<dubbo:consumer>的timeout | 性能调优 | 服务方法调用超时时间(毫秒) | 1.0.5以上版本 | 
| retries | int | 可选 | 缺省使用<dubbo:consumer>的retries | 性能调优 | 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0 | 2.0.0以上版本 | 
| connections | int | 可选 | 缺省使用<dubbo:consumer>的connections | 性能调优 | 对每个提供者的最大连接数，rmi、http、hessian等短连接协议表示限制连接数，dubbo等长连接协表示建立的长连接个数 | 2.0.0以上版本 | 
| loadbalance | string | 可选 | 缺省使用<dubbo:consumer>的loadbalance | 性能调优 | 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用 | 2.0.0以上版本 | 
| async | boolean | 可选 | 缺省使用<dubbo:consumer>的async | 性能调优 | 是否异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 | 2.0.0以上版本 | 
| generic | boolean | 可选 | 缺省使用<dubbo:consumer>的generic | 服务治理 | 是否缺省泛化接口，如果为泛化接口，将返回GenericService | 2.0.0以上版本 | 
| check | boolean | 可选 | 缺省使用<dubbo:consumer>的check | 服务治理 | 启动时检查提供者是否存在，true报错，false忽略 | 2.0.0以上版本 | 
| url | string | 可选 | | 服务治理 | 点对点直连服务提供者地址，将绕过注册中心 | 1.0.6以上版本 | 
| stub | class/boolean | 可选 | | 服务治理 | 服务接口客户端本地代理类名，用于在客户端执行本地逻辑，如本地缓存等，该本地代理类的构造函数必须允许传入远程代理对象，构造函数如：public XxxServiceLocal(XxxService xxxService) | 2.0.0以上版本 | 
| mock | class/boolean | 可选 | | 服务治理 | 服务接口调用失败Mock实现类名，该Mock类必须有一个无参构造函数，与Local的区别在于，Local总是被执行，而Mock只在出现非业务异常(比如超时，网络异常等)时执行，Local在远程调用之前执行，Mock在远程调用后执行。 | Dubbo1.0.13及其以上版本支持 | 
| cache | string/boolean | 可选 | | 服务治理 | 以调用参数为key，缓存返回结果，可选：lru, | threadlocal, | jcache等 | Dubbo2.1.0及其以上版本支持 | 
| validation | boolean | 可选 | | 服务治理 | 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验 | Dubbo2.1.0及其以上版本支持 | 
| proxy | boolean | 可选 | javassist | 性能调优 | 选择动态代理实现策略，可选：javassist, | jdk | 2.0.2以上版本 | 
| client | string | 可选 | | 性能调优 | 客户端传输类型设置，如Dubbo协议的netty或mina。 | Dubbo2.0.0以上版本支持 | 
| registry | string | 可选 | 缺省将从所有注册中心获服务列表后合并结果 | 配置关联 | 从指定注册中心注册获取服务列表，在多个注册中心时使用，值为<dubbo:registry>的id属性，多个注册中心ID用逗号分隔 | 2.0.0以上版本 | 
| owner | string | 可选 | | 服务治理 | 调用服务负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.0.5以上版本 | 
| actives | int | 可选 | 0 | 性能调优 | 每服务消费者每服务每方法最大并发调用数 | 2.0.5以上版本 | 
| cluster | string | 可选 | failover | 性能调优 | 集群方式，可选：failover/failfast/failsafe/failback/forking | 2.0.5以上版本 | 
| filter | string | 可选 | default | 性能调优 | 服务消费方远程调用过程拦截器名称，多个名称用逗号分隔 | 2.0.5以上版本 | 
| listener | string | 可选 | default | 性能调优 | 服务消费方引用服务监听器名称，多个名称用逗号分隔 | 2.0.5以上版本 | 
| layer | string | 可选 | | 服务治理 | 服务调用者所在的分层。如：biz、dao、intl:web、china:acton。 | 2.0.7以上版本 | 
| init | boolean | 可选 | false | 性能调优 | 是否在afterPropertiesSet()时饥饿初始化引用，否则等到有人注入或引用该实例时再初始化。 | 2.0.10以上版本 | 
| protocol | string | 可选 | | 服力治理 | 只调用指定协议的服务提供方，其它协议忽略。 | 2.2.0以上版本 |

## 8、<dubbo:module>

模块信息配置： 配置类：`com.alibaba.dubbo.config.ModuleConfig`

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| name | string | 必填 | | 服务治理 | 当前模块名称，用于注册中心计算模块间依赖关系 | 2.2.0以上版本 | 
| version | string | 可选 | | 服务治理 | 当前模块的版本 | 2.2.0以上版本 | 
| owner | string | 可选 | | 服务治理 | 模块负责人，用于服务治理，请填写负责人公司邮箱前缀 | 2.2.0以上版本 | 
| organization | string | | 可选 | 服务治理 | 组织名称(BU或部门)，用于注册中心区分服务来源，此配置项建议不要使用autoconfig，直接写死在配置中，比如china,intl,itu,crm,asc,dw,aliexpress等 | 2.2.0以上版本 |

## 9、<dubbo:method>

方法级配置：  
配置类：`com.alibaba.dubbo.config.MethodConfig`  
说明：该标签为`<dubbo:service>`或`<dubbo:reference>`的子标签，用于控制到方法级，

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| name | string | 必填 | | 标识 | 方法名 | 1.0.8以上版本 | 
| timeout | int | 可选 | 缺省为的timeout | 性能调优 | 方法调用超时时间(毫秒) | 1.0.8以上版本 | 
| retries | int | 可选 | 缺省为<dubbo:reference>的retries | 性能调优 | 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0 | 2.0.0以上版本 | 
| loadbalance | string | 可选 | 缺省为的loadbalance | 性能调优 | 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用 | 2.0.0以上版本 | 
| async | boolean | 可选 | 缺省为<dubbo:reference>的async | 性能调优 | 是否异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 | 1.0.9以上版本 | 
| sent | boolean | 可选 | true | 性能调优 | 异步调用时，标记sent=true时，表示网络已发出数据 | 2.0.6以上版本 | 
| actives | int | 可选 | 0 | 性能调优 | 每服务消费者最大并发调用限制 | 2.0.5以上版本 | 
| executes | int | 可选 | 0 | 性能调优 | 每服务每方法最大使用线程数限制，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效 | 2.0.5以上版本 | 
| deprecated | boolean | 可选 | false | 服务治理 | 服务方法是否过时，此属性只在<dubbo:method>作为<dubbo:service>子标签时有效 | 2.0.5以上版本 | 
| sticky | boolean | 可选 | false | 服务治理 | 设置true 该接口上的所有方法使用同一个provider.如果需要更复杂的规则，请使用用路由 | 2.0.6以上版本 | 
| return | boolean | 可选 | true | 性能调优 | 方法调用是否需要返回值,async设置为true时才生效，如果设置为true，则返回future，或回调onreturn等方法，如果设置为false，则请求发送成功后直接返回Null | 2.0.6以上版本 | 
| oninvoke | String | 可选 | | 性能调优 | 方法执行前拦截 | 2.0.6以上版本 | 
| onreturn | String | 可选 | | 性能调优 | 方法执行返回后拦截 | 2.0.6以上版本 | 
| onthrow | String | 可选 | | 性能调优 | 方法执行有异常拦截 | 2.0.6以上版本 | 
| cache | string/boolean | 可选 | | 服务治理 | 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等 | Dubbo2.1.0及其以上版本支持 | 
| validation | boolean | 可选 | | 服务治理 | 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验 | Dubbo2.1.0及其以上版本支持 |

比如：

```
<dubbo:reference interface="com.xxx.XxxService">
    <dubbo:method name="findXxx" timeout="3000" retries="2" />
</dubbo:reference>
```

## 10、<dubbo:argument>

方法参数配置：  
配置类：`com.alibaba.dubbo.config.ArgumentConfig`  
说明：该标签为`<dubbo:method>`的子标签，用于方法参数的特征描述，比如：

```
<dubbo:method name="findXxx" timeout="3000" retries="2">
    <dubbo:argument index="0" callback="true" />
</dubbo:method>
```

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| index | int | 必填 | | 标识 | 方法名 | 2.0.6以上版本 | 
| type | String | 与index二选一 | | 标识 | 通过参数类型查找参数的index | 2.0.6以上版本 | 
| callback | boolean | 可选 | | 服务治理 | 参数是否为callback接口，如果为callback，服务提供方将生成反向代理，可以从服务提供方反向调用消费方，通常用于事件推送. | 2.0.6以上版本 |

## 11、<dubbo:parameter>

选项参数配置：  
配置类：`java.util.Map`

说明：该标签为`<dubbo:protocol>`或`<dubbo:service>`或`<dubbo:provider>`或`<dubbo:reference>`或`<dubbo:consumer>`的子标签，用于配置自定义参数，该配置项将作为扩展点设置自定义参数使用。

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| key | string | 必填 | | 服务治理 | 路由参数键 | 2.0.0以上版本 | 
| value | string | 必填 | | 服务治理 | 路由参数值 | 2.0.0以上版本 |

比如：

```
<dubbo:protocol name="napoli">
    <dubbo:parameter key="http://10.20.160.198/wiki/display/dubbo/napoli.queue.name" value="xxx" />
</dubbo:protocol>
```

也可以：

```
<dubbo:protocol name="jms" p:queue="xxx" />
```

## 12、<dubbo:monitor>

监控中心配置：  
配置类：`com.alibaba.dubbo.config.MonitorConfig`

| 属性 | 类型 | 是否必填 | 缺省值 | 作用 | 描述 | 兼容性 |
| ------------- | ------------- | ----- | ----- | ----- | ----- | ----- |
| protocol | string | 可选 | dubo | 服务治理 | 监控中心协议，如果为protocol="registry"，表示从注册中心发现监控中心地址，否则直连监控中心。 | 2.0.9以上版本 |
| address | string | 可选 | N/A | 服务治理 | 直连监控中心服务器地址，address="10.20.130.230:12080" | 1.0.16以上版本 |