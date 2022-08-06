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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Author extends BaseUser {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespondedRequest> respondedRequests;
}
