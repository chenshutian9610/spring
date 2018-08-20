package tree.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tb_order")
public class Order {
    private int id;
    private String goods;
    private int price;
    private Customer customer;

    public Order(){}
    public Order(String goods,int price){
        this.goods=goods;
        this.price=price;
    }

    @Id
    @GeneratedValue(generator = "myGenerator")
    @GenericGenerator(name="myGenerator",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "c_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "order: "+goods+"\t"+price;
    }
}
