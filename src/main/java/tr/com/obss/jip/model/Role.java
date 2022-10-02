package tr.com.obss.jip.model;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    @NotNBE
    private RoleType name;
}
