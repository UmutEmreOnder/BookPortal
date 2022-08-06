package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class User extends BaseUser {
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Book> readList;


    @OneToMany(cascade = {CascadeType.ALL})
    private List<Book> favoriteList;
}
