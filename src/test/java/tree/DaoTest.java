package tree;

import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.dbunit.dataset.util.XlsDataSetBeanFactory;
import tree.domain.Customer;
import tree.domain.IDCard;

import java.util.List;

public class DaoTest extends BaseTest {

    @Test
    /**
     *  @DataSet 中的数据会在方法开始之前加载进数据库
     *  括号中指定的文件必须与当前类在同一个包中（不是在类路径下)，下面的 @ExpectedDataSet 也一样
     */
    @DataSet("init_tables.xls")
    public void initTables(){}

    @Test
    /**
     *  @ExpectedDataSet 会在方法结束后比较数据库中的数据
     *  通常用来测试 dao 层的方法
     */
    @ExpectedDataSet("expected_rows.xls")
    public void saveCustomers() throws Exception{
        /**
         *  XlsDataSetBeanFactory 是别人编写的示例代码，用来将 excel 文件中的行数据转为对象
         *      createBeans     转化多行
         *      createBean      转化一行
         */
        List<Customer> customers= XlsDataSetBeanFactory.createBeans(this.getClass(),"expected_rows.xls","tb_customer",Customer.class);
        List<IDCard>idCards=XlsDataSetBeanFactory.createBeans(this.getClass(),"expected_rows.xls","tb_idCard",IDCard.class);
        for(int i=0;i<customers.size();i++)
            customers.get(i).setIdCard(idCards.get(i));
        dao.saveCustomers(customers);
//        dao.saveIdCards(idCards);
    }
}
