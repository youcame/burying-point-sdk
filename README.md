## 链路追踪系统

### 作用
在现有系统十分完善的情况下，出现线上客诉时，问题仍旧会一层层传递，最终走到研发侧。在系统含有众多业务线的场景下，研发侧为解决线上问题，依然逃不开花费大量时间查看日志、运维后台等以排查客诉问题。

在这样的背景下，链路追踪系统诞生了，它使得任何与系统相关的人员，都能够零门槛地快速定位某一个用户在近期内，所调用的接口参数以及接口返回值，大大地减少了研发人员处理客诉问题所需要的时间。

### 功能展示
![image](https://github.com/user-attachments/assets/38b8978f-653b-4e58-b166-27fefddf7da0)

![image](https://github.com/user-attachments/assets/c8ea2f59-ed14-49e9-b438-b60abea8726a)

### 接入
1. 引入pom包
```
<dependency>
  <groupId>com.neu</groupId>
  <artifactId>burying-point-sdk</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2. 引入接入系统的sysId、token、sysDescription等，以表示你所代表的业务线，在yaml文件中写入
```
travel:
  track:
    sys-description: helps more people find there teammates with same interest
    sys-id: group-finder
    token: asdasadsadaasdasd
```   
4. 自定义记录流程、可采用异步发送MQ或写入数据库等，由一个统一的系统进行存储；自定义处理类，由于不同的系统在返回的数据结构不同（例如存在subMessage），可自定义处理类，并在注解中传入，不实现可采用默认；自定义参数处理类，若方法的某些参数sdk中不存在，可自定义参数的记录方法。
 
5. 在需要记录的方法上加入注解：@Record()，传入参数bid、isRecord（即是否进行埋点上报的开关）与上述实现类，完成埋点的上报与记录。

### 实现
当今大部分主流rpc框架都支持提供上下文的功能，以便存储与记录rpc调用时的一些信息，在该sdk中，我们将在上下文中存储调用的链路步骤数：若A调用了B、C、D三个接口，则A中存储为步骤1，B、C、D分别存储2_1,2_2,2_3,依次类推...
![step递增规则](https://github.com/user-attachments/assets/65946e8b-1ced-4a8e-89af-9d3e66daf4ae)

参数以及调用结果，采用aop与反射的方式的获取，默认会获取参数中的pin、channel、source等通用字段，支持接入方自行实现参数的获取。

最重要的存储功能由用户自定义实现，该sdk负责入参与出参的封装，推荐用户采用MQ的方式将信息传递给埋点记录系统，由埋点记录系统消费MQ，将信息存储在表中供大家查询。

由于该系统的接入可能造成性能的损耗，**不推荐**对读链路进行操作，并且用户在执行记录操作时，推荐采用**异步**的方式（例如异步发MQ、写库）

在埋点记录系统记录与展示时，应按照某个唯一键（此处为pin），并且按照客户端上传的发送时间（由于是异步，不能为服务端的消费时间），进行串行展示，以达成展示效果。


