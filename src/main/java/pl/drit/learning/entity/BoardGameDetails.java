package pl.drit.learning.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "boardgames")
@EntityListeners(AuditingEntityListener.class)
@Setter
@NoArgsConstructor
public class BoardGameDetails {

    private long id;
    private String title;
    private double price;
    private String link;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    @Column(name = "link")
    public String getLink() {
        return link;
    }
}
