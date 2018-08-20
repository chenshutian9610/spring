package tree.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_idCard")
public class IDCard {
    private int id;
    private long num;
    private Customer customer;

    public IDCard(){}
    public IDCard(int num){this.num=num;}

    @Id
    @GeneratedValue(generator = "myGenerator")
    @GenericGenerator(name="myGenerator",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNum() { return num; }

    public void setNum(long num) { this.num = num; }

    @OneToOne(mappedBy = "idCard")  //  idCard 是 Customer 的属性
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
