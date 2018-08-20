package tree;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;
import tree.dao.BaseDao;
import tree.domain.Customer;
import tree.domain.IDCard;

import java.util.Base64;

import static org.testng.Assert.assertNotNull;

public class MyTest{
    @Test
    public void doInTest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        HibernateTemplate session=context.getBean(HibernateTemplate.class);
        BaseDao dao=context.getBean(BaseDao.class);
        IDCard idCard=new IDCard(1406448399);
        Customer customer=new Customer("triski");
        customer.setIdCard(idCard);
        dao.saveCustomer(customer);
        System.out.println(session.get(Customer.class,1));
    }
}