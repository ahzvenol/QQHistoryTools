## QQHistory Export
QQ聊天记录工具的后端模块之一,用于本地消息数据库的导出,目前计划支持Android和Windows端的数据解析,是[Hakuuyosei/QQHistoryExport](https://github.com/Hakuuyosei/QQHistoryExport)项目的不兼容重构

目前只编写并成功运行了一些解析相关的测试函数,后续代码缓慢编写中...

## 目前已知信息
### Android端
#### 位置
消息存储在`/data/data/com.tencent.mobileqq/databases/`下的`{QQ号}.db`和`slowtable_{QQ号}.db`,其中slowtable存储时间较为久远的消息

好友消息位于`mr_friend_{md5-32Upper(好友QQ号)}_New`表中

群聊消息位于`mr_troop_{md5-32Upper(群号)}_New`表中

`{QQ号}-IndexQQMsg.db`里没有什么有效信息

#### 字段

`senderuin`是此条消息**发送者**的**QQ号**

`frienduin`是此条消息**发送地点**的**QQ号/群号**

`msgData`是消息的**主体**,大部分信息在这里面

`msgtype`标识消息的**类型**,不同的消息类型需要不同的读取方式

`extStr`根据消息的**类型**存储不同的信息,其中包括回复的**引用信息**

`time`是此条消息发送时间的**时间戳**,位数为**十位**

`shmsgseq`是消息的**序列号**,意思是short`msgseq`,由以下转换得到
```java 
short shmsgseq = (short)(int)msgseq
```

`msgUid`是Random随机摇出来的,对于提取消息没什么用处

`uniseq`(大部分)由`time`做以下转换得到,同样意义不大
```java 
long uniseq = ((short)(int)msgseq) << 32
```
其他字段比较显然不包含有效信息,就不再逐一说明

#### 消息解析

已知消息的序列化方式包括<br/>
`json`(常见的数据传输格式)<br/>
`Java Serialization`(Java内置的序列化方式)<br/>
`protobuf`(Google提出的一种序列化方式)<br/>
`XmlBuff`(疑似腾讯自己实现的序列化方式)<br/>

其中json格式很容易解析;java序列化需要通过对QQ代码中的相关数据类进行模拟,然后通过Java内置类`ObjectInputStream`进行解析;protobuf需要通过参考QQ代码中对应数据类的`__fieldMap__`,然后还原.proto文件并通过protobuf提供的代码生成器生成解析代码,或利用__fieldMap__中提供的信息和黑盒解析方式提取出需要的数据;XmlBuff格式目前解析方式不明

具体各消息类型对应的序列化方式见[QQ消息格式.xlsx
](https://github.com/Hakuuyosei/QQHistoryExport/blob/master/docs/QQ%E6%B6%88%E6%81%AF%E6%A0%BC%E5%BC%8F.xlsx)

### Windows端
#### 位置
消息存储在`个人文件夹\<QQ号>\Msg3.0.db`下的`Msg3.0.db`

个人文件夹的位置取决于你在QQ中的设置,默认为`C:\Users\<用户名>\Documents\Tencent`

好友消息位于`buddy_<好友QQ号>`表中

群聊消息位于`group_<好友QQ号>`表中

消息的`source`位于对应的`$Seq$`表中

#### 字段

`SenderUin`是此条消息**发送者**的**QQ号**

`MsgContent`是消息的**主体**,大部分信息在这里面

`Time`是此条消息发送时间的**时间戳**,位数为**十位**

`Rand`推测是随机数,可用于在对应的`$Seq$`表中查询消息的`source`

`Info`字段暂未解析,内容未知

`$Seq$`表中的`Seq`字段是消息的**序列号**

#### 消息解析

消息的序列化方式为**TLV**(Tag-Length-Value)格式,字符串类型数据编码为**UTF-16LE**,数值类型都为**无符号类型**