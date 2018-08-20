package tree;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByType;
import tree.dao.BaseDao;
import tree.domain.Customer;
import tree.domain.IDCard;
import tree.domain.Order;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 *  类级别的 @SpringApplicationContext 在测试方法运行时只加载一次
 */
@SpringApplicationContext("applicationContext.xml")
/**
 *  Unitils 依赖于 TestNG 或 Junit，所以需要继承 UnitilsTestNG 或 UnitilsJUnit4
 */
public class BaseTest extends UnitilsTestNG {

    @SpringApplicationContext public ApplicationContext context;

    @SpringBean("session") public HibernateTemplate session;

    @SpringBeanByType public BaseDao dao;

    /**
     *  测试一下是否成功加载 spring 容器
     *  下面三个方法是连在一起的，data() 给 saveOrders() 提供数据，saveOrders() 依赖 saveCustomer()
     *  注意，这里虽然使用了 BaseDao，但这不是 dao 层的测试，因为 dao 测试会使用到 @ExpectedDataSet 对比数据库数据
     */
    private Customer customer;
    @Test
    public void saveCustomer(){
        IDCard idCard=new IDCard(1406448399);
        customer=new Customer("triski");
        customer.setIdCard(idCard);
        dao.saveCustomer(customer);
    }
    @DataProvider(name = "orders")
    public Object[][] data(){
        return new Object[][]{
                {"apple",3},
                {"watermelon",6},
                {"strawberry",11}
        };
    }
    @Test(dataProvider = "orders",dependsOnMethods = "saveCustomer")
    public void saveOrders(String goods,int price){
        Order order=new Order(goods,price);
        order.setCustomer(customer);
        dao.saveOrder(order);
    }
}