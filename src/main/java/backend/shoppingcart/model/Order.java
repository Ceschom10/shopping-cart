package backend.shoppingcart.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "order",schema = "sch_shopping")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;
	
	private String cliente;
	
	private double total;
	
	@OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
