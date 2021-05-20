package am.itspace.bigbag.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "'order'")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User userId;
    @ManyToOne
    private Product productId;
    private String address;
    private Date remarkDate;
    private Date shipmentDate;
    private String productCode;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PROCESSING;
}
