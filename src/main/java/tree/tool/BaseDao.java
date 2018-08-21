package tree.tool;

import tree.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional  //  一般情况下，tool 层不需要使用事务，但这里使用了 hibernate 框架（Session 依赖事务环境）
public class BaseDao {
    @Autowired
    private HibernateTemplate session;

    public void saveCustomer(List<Customer>customers){
        for(Customer customer:customers)
            session.save(customer);
    }

    public void saveCustomer(Customer customer){
        session.save(customer);
    }

    public void saveOrder(Order order){
        session.save(order);
    }

    public void saveOrder(List<Order> orders){
        for(Order order:orders)
            session.save(order);
    }

    public void saveIdCards(List<IDCard>idCards){
        for(IDCard idCard:idCards)
            session.save(idCard);
    }

    public Customer findCustomerByName(String name){
        return (Customer) session.find("from Customer c where c.name=?",name).get(0);
    }
    public IDCard findIDCardByCustomer(Customer customer){
        return (IDCard)session.find("select i from IDCard i,Customer c where i=c.idCard and c=?",customer).get(0);
    }
}
