package tree;

import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.spring.annotation.SpringBeanByType;
import tree.tool.BaseDao;
import tree.domain.Customer;
import tree.domain.IDCard;
import tree.tool.BaseService;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class ServiceTest extends BaseTest{
    /**
     *  单元测试，需要忽略所有外在因素，只关注方法的逻辑，
     *  所以需要使用模拟对象代替外在因素（这里使用 mockito 框架）
     */
    @Test
    public void unitTest(){
        /**
         *  初始化模拟对象，只需要使用 mock 方法，
         *  如果模拟对象太多的话，可以使用 @Mock 标志出模拟对象，
         *  然后使用 MockitoAnnotations.initMocks(this) 一次性初始化
         */
        BaseDao baseDao=mock(BaseDao.class);

        /**
         *  设置模拟数据（当模拟对象使用指定方法时会返回期望的结果），没有设置数据的返回 null
         *  设置语句也可以写成三段式：doReturn(customer).when(baseDao).findCustomerByName("triski");
         */
        Customer customer=new Customer("triski");
        IDCard idCard=new IDCard(1406448399);
        when(baseDao.findCustomerByName("triski")).thenReturn(customer);
        when(baseDao.findIDCardByCustomer(customer)).thenReturn(idCard);

        /**
         *  使用 spring 提供的工具类将 baseDao 注入到 service 对象中
         */
        BaseService service=new BaseService();
        ReflectionTestUtils.setField(service,"tool",baseDao);

        /**
         *  测试结果
         *  ReflectionAssert 是 Unitils 提供的工具类，通过重写 equals 比较两个对象的内容而不是引用，
         *  方法名中带有 reflection 为严格比较，带有 lenient 为宽松比较（忽略顺序等因素，如两个内容相同但顺序不同的数组）
         */
        IDCard result=service.findIDCardByCustomerName("triski");
        ReflectionAssert.assertPropertyReflectionEquals("num",1406448399,result);
        ReflectionAssert.assertReflectionEquals(idCard,result);

        /**
         *  测试交互次数，在 verify 中可以使用 times，atLeast，atMost 设置期望次数
         *  下列方法测试 baseDao 中指定方法的使用频率，如果与 times 不符则报错
         */
        verify(baseDao,times(1)).findCustomerByName("triski");
        verify(baseDao,times(1)).findIDCardByCustomer(customer);
    }

    @SpringBeanByType private BaseService service;
    /**
     *  集成测试
     *  service 层的集成测试一般和 @DataSet 配合（数据的重置，有利于重复测试）
     */
    @Test
    @DataSet("init_tables.xls")
    public void integrationTest(){
        long num=service.findIDCardByCustomerName("Mike").getNum();
        assertEquals(num,13829576581L);
    }
}
