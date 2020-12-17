package pl.drit.learning.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.drit.learning.LinkAddress;

import javax.persistence.*;

@Entity
@Table(name = "boardgames")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
public class BoardGameDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private double price;
    @LinkAddress
    @Column(name = "link", unique = true)
    private String link;

}
