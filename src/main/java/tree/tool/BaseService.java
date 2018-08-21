package tree.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tree.domain.Customer;
import tree.domain.IDCard;
import tree.domain.Order;

import java.util.List;

@Service
@Transactional
public class BaseService {
    @Autowired
    private BaseDao dao;

    public IDCard findIDCardByCustomerName(String name){
        Customer customer=dao.findCustomerByName(name);
        return dao.findIDCardByCustomer(customer);
    }

    public void addOrders(List<Order> orders){
        dao.saveOrder(orders);
    }
}
