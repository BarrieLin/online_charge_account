# Online-Charge-Account

*Builded in 2021/4/16*

---

# 项目规范

## springboot规范

有句话说得好**"约定大于配置"**

* 环境规范

  * java version "1.8.0_202"
  * mysql 8.0

* 编码规范

  * 类和接口首字母大写，遵循驼峰命名结构

  * 变量名及方法名首字母小写，遵循驼峰命名结构

  * 类的属性的类型、方法返回值的类型全为类

  * 删除、修改、插入的方法都不要返回值（调试的时候可以有，但是push上coding的最终代码不要）

  * 项目java包结构及规范

    * controller包

      控制层，装载控制类

      * 命名为XxxController

        示例：UserController

      * 方法名为功能名，如学生登录功能即为：studentLogin

      * 注解@RequestMapping里面的URL全为小写，单词之间用"_"连接

      * 方法的传入参数全部为对象，并且传入的对象前需要增加一个@RequestBody注解

        示例：

        ```java
        @RequestMapping("student_login")
        public Integer studentLogin(@RequestBody Student student)
        ```

        若传入的参数为int -> Integer|float -> Float|double -> Double等类型则不需要添加@RequestBody注释

        示例

        ```java
      @RequestMapping("oca_select_allBill_byUserId")
        public Integer selectUserAllBills(Integer userId)
        ```

      * 除返回前端页面@Controller外的其他controller，全部使用@RestController，即全部返回json格式的数据

    * service包

      业务层，装载业务接口和业务实现类

      * 命名为XxxService和XxxServiceImpl
    * 方法名为处理的业务，方法传入的参数同上为对象，返回的也为对象
  
  * dao包
  
      数据库接口
  
    * 命名为XxxDao
  
    * 方法名为动作+方式
  
      示例：
  
      ```java
        Student findById(Student student)
      ```
  
    * 方法的传入参数和返回值均为对象
  
  * po包
  
    用于装实体类（与数据库对应的类也好，外模式的类也好）
  
    * 实体类无需加入任何注解
  
      * 所有属性全为私有
  
      * 除固定标识状态外的其他属性都要导入get和set方法

      * 对除固定标识状态外的其他属性要重载到toString()之中

      * 固定标识状态写死，使用final static，如：
  
        ```java
      public final static logined = 1;
        public final static unlogined = 0;
        ```
      ```
      
      ```
  
    * 所有属性的类型都是类
  
      ```java
        int -> Integer|float -> Float|double -> Double and so on
      ```
  
      通过属性是否为**null**，即可判断是否接收到传参，是否成功接收到数据库映射的值
  
  * util包
  
    工具包，存放可供整个项目多次重复使用的工具类
  
* 项目resources下mapper目录
  
    存放进行映射的xml文件
  
    * 命名全部小写，单词之间用"-"连接，结尾一定要加上mapper
    
      示例：bill-mapper.xml
    
    * xml固定模板
    
      ```xml
      <?xml version="1.0" encoding="UTF-8" ?>
      <!DOCTYPE mapper
              PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
      <mapper namespace="com.kubi.lovespread.mapper.BillMapper">
        <select id="getBillByUser" resultType="com.kubi.lovespread.po.Bill">
              select bill_id, unique_id, community_id, appellation, room_number,
            phone, start_time, end_time, device_id, money, total_money
              from bill where unique_id = #{uniqueId}
        </select>
      </mapper>
      ```
    
    * xml的sql语句规范
    
      * 查询绝对不要写通配符**~~select * from xxx~~**，一定要把所有字段都写出来
    
        示例：
    
        ```xml
        select bill_id, unique_id, community_id, appellation, room_number,
                phone, start_time, end_time, device_id, money, total_money
                from bill where unique_id = #{uniqueId}
        ```


## 数据库命名规范

* 表名、字段名全部小写，多个单词中间用"_"连接（**一定要照这个规范！！！，mybatis已经配置驼峰转换，方便编程**）

**time 2020/12/25 11:10**

**by SuperKuBi**

---

# 代码仓库的使用

## Git最新镜像

http://npm.taobao.org/mirrors/git-for-windows/v2.31.1.windows.1/Git-2.31.1-64-bit.exe

## 仓库初使用

### 五分钟速食餐

* git clone + 仓库克隆地址

  > 在本地建一个存放git项目的文件夹，在git bash上cd到该文件内，使用上面命令把项目荡下来。输入命令后ls一下，就会看到荡下来项目的文件夹，cd进去就是和远程仓库连接上的本地仓库。

* git pull origin master

  > 将远程仓库的master分支的最新内容拉到本地当前分支下。在本地仓库下使用该命令；拉取最新的项目内容，每次动工之前**必拉！**

* git push origin master

  > 将本地当前分支推送到远程仓库的master分支上。

* git status, git add, git commit

  > 这三个命令是管理本地仓库必须用到的，**git status**查看当前本地仓库的状态，**git add + 文件名**将未追踪(untracked)的文件（或修改的文件）加入到暂存区，**git commit -am "项目变动描述内容"**将修改的内容提交到本地仓库，形成一个新的版本。这里的**-am**可以同时把修改的文件直接提交，可以偷懒省略git add，但是未追踪文件还是得先git add。**注意：<u>每次提交一定要确保无误，并且一定要写注释（本次修改了什么，增加了什么）！！！</u>**git commit过后的仓库，就可以推到远程仓库上即**git push origin master**。

## Git系统学习

​	上述的速食餐已经可以满足我们团队大部分的使用，但是有时可能会出现别人推上新的版本到本地仓库，而你刚好要把你写好的最新版本推上去，这时必发生错误冲突！当你推上去时候，远程仓库将拒绝推送请求。此时解决做法是**git pull origin master**拉下最新版本，**git status**查看下状态，接着解决冲突文件，再接着提交到本地仓库**（同如上注意内容）**，再push上远程仓库。倘若运气特别棒，别人又抢先提交了，那就又多一次练习机会，岂不美哉 -v- 。

​	其实剩下就是**分支**、**版本回退控制**和**工作区—暂存区—版本库之间的关系**等。

​	系统学习见：https://www.liaoxuefeng.com/wiki/896043488029600

**Time:2021/4/16 16:56**

**By SuperKuBi**

---

# 在线记账app功能

## 用户

* 账单管理（金额，账单类型，花销类型，备注）
  * 添加账单
    * 选择账单（收入、支出）类型
    * 选择花销类型(餐饮，交通，购物等等)
      * 支出类型(餐饮，交通，购物，医疗，娱乐，学习，金融，转账)
      * 收入类型(生活费，发工资，收红包，股票基金)
  * 修改账单
  * 删除账单
  * 查询账单
    * 根据年月日与用户账号(userId)查询
    * 根据年月与用户账号(userId)查询
    * 根据年与用户账号(userId)查询
    * 根据账单id查询
* 流水
  * 显示年、月、日度收入、支出统计数值
    * (根据花销分类(收入、支出)与用户账号(userId)查询支出与收入总和)
  * 显示至今为止收入、支出统计数值
    * (根据用户账号查询所有账单并统计数值)
  * 显示所有时间段的添加账单记录
    * 账单记录显示类型和金额（详细内容再点击账单记录项，点进后可修改和删除）
  * 收入、支出目标设置**（暂定不需要）**
  * 年、月、日度预算设置//修改为月度预算设置

- 图表展示
  - 显示年、月、日度(餐饮，交通，购物等等)各类收入、支出统计占比图
    - (根据账单分类(餐饮，交通，购物等等)与用户账号(userId)查询支出总和)
- 显示月度预算值，显示预算超出，预算未超出的值（月预算）
  
- 用户信息管理（用户名，密码，头像，昵称）
  - 注册登录功能
  - 修改个人信息

* 其他功能
  * 同步本地账本**（暂定不需要）**
  * 支出、收入分类管理**（暂定不需要）**
    * 增加支出、收入类型
    * 删除支出、收入类型

**time: 2021/05/28 21:54**

**By 工头**

------

# 功能逻辑（未完待续）

## 月份预算表

> * 用户id和时间（某年某月）做主键：通过查询表内是否有这条记录来确定是增加月份预算还是修改月份预算

## 账单表

> * 某个用户查询自己的账单：通过匹配用户id来获取历史账单
> * 修改账单：通过账单id对新的数据进行修改
> * 添加账单：系统自增账单id且必须填入用户Id

## 登录逻辑

> 1. 查找是否有用户名，有则验证密码，无则提醒注册。
> 2. 注册时，查询用户名，若存在不让注册。

# 数据库表（未完待续）

## 详细表信息

| 表名:用户信息表（oca_user） |          |         |      |           |          |            |          |
| --------------------------- | -------- | ------- | ---- | --------- | -------- | ---------- | -------- |
| 中文名                      | 英文名   | 类型    | 长度 | 是否为空  | 是否主键 | 备注       | 介绍说明 |
| 帐号                        | user_id  | varchar | 32   | not  null | pk       |            |          |
| 密码                        | password | varchar | 32   | not  null |          |            |          |
| 昵称                        | nickname | varchar | 32   | not  null | unique   | 默认为账号 |          |
| 头像                        | photo    | varchar | 255  | not  null |          | 默认头像   |          |

| 表名:月份预算表（budget） |               |          |      |           |          |      |          |
| ------------------------- | ------------- | -------- | ---- | --------- | -------- | ---- | -------- |
| 中文名                    | 英文名        | 类型     | 长度 | 是否为空  | 是否主键 | 备注 | 介绍说明 |
| 用户ID                    | user_id       | varchar  | 32   | not  null | pk       |      |          |
| 时间                      | month_time    | datetime |      | not  null | pk       |      |          |
| 金额                      | predict_money | float    | 32   | not  null |          |      |          |

| 表名:账单表(bill) |              |          |      |           |          |                  |      |
| ----------------- | ------------ | -------- | ---- | --------- | -------- | ---------------- | ---- |
| 中文名            | 英文名       | 类型     | 长度 | 是否为空  | 是否主键 | 介绍说明         | 备注 |
| 账号              | user_id      | varchar  | 255  | not  null |          | 用户账号         |      |
| 自增ID            | bill_id      | varchar  | 255  | not  null | pk       | 账单自增ID       |      |
| 金额              | pay_money    | varchar  | 255  | not  null |          | 消费金额         |      |
| 时间              | time         | datetime |      | not  null |          | 消费时间         |      |
| 类型              | check_type   | int      | 11   | not  null |          | 消费类型         |      |
| 备注              | remark       | varchar  | 255  | not  null |          |                  |      |
| 状态              | check_status | int      | 11   | not  null |          | 0：收入，1：支出 |      |

## 建表代码

```mysql
create database Online_Charge_Account;
<!--旧表格： -->
drop table if exists oca_user;
create table oca_user(
user_id int primary key auto_increment,
password varchar(32) not null,
nickname varchar(32) unique not null,
photo varchar(255) not null);

<!--新表格：把oca_user的user_id的类型修改成varchar(32) -->
DROP TABLE IF EXISTS `oca_user`;
CREATE TABLE `oca_user` (
  `user_id` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

<!--旧表格： -->
drop table if exists budget;
create table oca_budget(
user_id int,
month_time varchar(32),
predict_money float,
primary key(user_id,month_time));

<!--新表格：把oca_budget的user_id的类型修改成varchar(32) -->
DROP TABLE IF EXISTS `oca_budget`;
CREATE TABLE `oca_budget` (
  `user_id` varchar(32) NOT NULL,
  `month_time` varchar(32) NOT NULL,
  `predict_money` float DEFAULT NULL,
  PRIMARY KEY (`user_id`,`month_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

<!--旧表格： -->
drop table if exists oca_bill;
create table oca_bill(
bill_id int primary key auto_increment,
user_id int not null,
pay_money float not null,
time datetime not null,
check_type int not null,
remark varchar(255) not null,
check_status int not null);

<!--新表格：把oca_bill的user_id的类型修改成varchar(32) -->
DROP TABLE IF EXISTS `oca_bill`;
CREATE TABLE `oca_bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL,
  `pay_money` float NOT NULL,
  `time` datetime NOT NULL,
  `check_type` int(11) NOT NULL,
  `remark` varchar(255) NOT NULL,
  `check_status` int(11) NOT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
```

**Time:2021-5-25 15:02:42**

**By SuperKuBi**

**Time:2021-5-28 18:09作数据库表格新的修改**

**By 工头**

---

# 项目开发

## 写在前头

* 统一使用洲洲服务器数据库（在springboot已经配置好了，自己可用navicat连数据库，账号、密码和ip在application-dev.yml里面）
* 统一后端springboot环境（pull 下来，idea下载依赖完成后，运行查看localhost:8080/testOne出现**account system first controller test. 1**即环境正确）
* 统一url为**oca**开头，多个单词小写且用 **_** 连接。如**oca_login**
* 对url的测试可用Postman进行

# 项目进展

## 登录注册模块

> 后端：
>
> * 已完成：
>   * 登录注册逻辑已经实现
> * 未完成：
>   * 登录注册后的session处理
>   * 未对接受数据进行非空等检测
>
> 前端：
>
> - 已完成：
>   - 登陆注册相关界面
>   - 登录注册逻辑与后端的连接

---

## 账单(记账)管理模块

> 后端：
>
> * 已完成：
>   * 添加账单
>   * 修改账单
>   * 删除账单
>   * 查询账单
>     * 根据账单id查询
> * 未完成：
>
> 前端：
>
> - 已完成：
>   - 添加账单（包括支出、收入）界面并与后端连接
> - 未完成：
>   - 添加、修改、删除、查询账单对应后端未与前端界面进行连接
>   - 修改、删除账单界面

---

## 流水模块

> 后端：
>
> - 已完成：
>   - 按照年月以及用户ID查询账单
> - 未完成：
>
> 前端：
>
> - 已完成：
>   - 显示根据时间选择显示月支出月收入的界面
>   - 按年月显示账单并与后端连接（显示用户账单列表）
> - 未完成：
>   - （流水界面上面那部分）显示月支出月收入的界面还未与后端连接

---

## 个人中心模块

> 后端：
>
> - 已完成：
> - 未完成：
>   - 修改个人信息
>   - 查询个人信息
>
> 前端：
>
> - 已完成：
>   - 个人中心主界面
> - 未完成：
>   - 个人中心内各个控件的跳转
>   - 未与后端连接

---

## 图表模块

> 后端：
>
> - 已完成：
>   - 完成图表后端传给前端必要数据的逻辑
> - 未完成：
>
> 前端：
>
> - 已完成：
>   - 月收支统计表
>   - 当月支出百分比占比图
>   - 全部账单收入支出总和展示栏
>   - 与后端连接已完成
> - 未完成：

---

**time: 2021/05/29 22:51**

**By 工头**