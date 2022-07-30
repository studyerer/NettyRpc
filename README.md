# NettyRpc

### spring netty 泛型 反射 自定义注解 序列化 rpc

一般来说RPC的调用过程如下：
![image](https://user-images.githubusercontent.com/38463284/181879028-19a2dbf1-8201-4e01-8e51-fed9074f2178.png)

1.client 会调用本地动态代理 proxy   
2.这个代理会将调用通过协议转序列化字节流  
3.通过 netty 网络框架，将字节流发送到服务端  
4.服务端在受到这个字节流后，会根据协议，反序列化为原始的调用，利用反射原理调用服务方提供的方法  
5.如果请求有返回值，又需要把结果根据协议序列化后，再通过 netty 返回给调用方  

### 项目包结构如下：
![image](https://user-images.githubusercontent.com/38463284/181879102-1e65c234-30ce-4a60-8ee7-aca39c78ec5a.png)

clinet就是调用方。servive是服务的提供者。protocol包定义了通信协议。common包含了通用的一些逻辑组件。使用 maven 作为包管理工具，json 作为序列化协议，使用spring boot管理对象的生命周期，netty 作为 nio 的网路组件。  

protocol包：  
作为 RPC 的协议，负责把一次本地方法的调用，变成能够被网络传输的字节流。  
   
service 包：   
负责处理客户端请求的组件，使用 Nio 非阻塞的方式处理高并发的场景，netty 是一个优秀的 Nio 处理框架。   
使用基于反射的cglib，来打理服务端的相关方法。  

client 包：   
netty 是一个异步框架，所有的返回都是基于 Future 和 Callback 的机制。因此用Map建立一个 ID 和 Future 映射，当一个结果异步的返回后。这样请求的线程只要使用对应的 ID 就能获取，相应的返回结果。同时在框架中使用动态代理替换原有方法，直接调用远程方法。   

spring 集成：   
自定义注解，使用Spring 扫描到这个注解后，创建代理对象，并将bean存放在 spring 的 applicationContext 中。后面即可使用autowired 导入bean.
