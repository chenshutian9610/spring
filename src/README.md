* 此项目使用 Spring 整合 Hibernate，并使用 Unitils 和 TestNG 进行测试
* 版本：
    Spring 4.2.9
    Hibernate 5.0.5
    TestNG 6.9.10
    Unitils 3.4.6       //  ps：（3.4.2 版本最高只支持 Hibernate 4）

* 在 domain 中，Customer 和 IDCard 是一对一关系，Customer 和 Order 是一对多关系

* org.dbunit 包下的四个类是第三方扩展的
    MySqlDbUnitModule               测试数据源的加载模块
    MultiSchemaXlsDataSetFactory    读取 excel 的加载模块
    MultiSchemaXlsDataSetReader     excel 文件的读取逻辑
    XlsDataSetBeanFactory           将 excel 文件中数据转化为 Bean

* 测试类全部在 tree 包中，BaseTest 为基础类，其他类都是从它扩展出来的
* 测试资源：
    unitils.properties      Unitils 的项目级配置文件
    001_testdb.sql          数据库结构的初始化
    init_tables.xls         表初始化
    expected_rows.xls       测试 dao