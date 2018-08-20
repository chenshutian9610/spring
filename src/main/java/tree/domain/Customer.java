package tree.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tb_customer")
public class Customer {
    private int id;
    private String name;
    private Set<Order>orders=new HashSet<Order>();
    private IDCard idCard;

    public Customer(){}
    public Customer(int id){this.id=id;}
    public Customer(String name){ this.name=name; }

    @Id
    @GeneratedValue(generator = "myGenerator")
    @GenericGenerator(name="myGenerator",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "customer")   //  customer 是 Order 的属性
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "num_id")
    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "customer: "+name;
    }
}
