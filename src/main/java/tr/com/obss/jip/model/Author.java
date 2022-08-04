package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String name;

    @NotNBE
    private String surname;

    @NotNBE
    @Column(unique = true, nullable = false)
    private String username;

    @NotNBE
    private String password;

    @ManyToMany
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespondedRequest> respondedRequests;
}
