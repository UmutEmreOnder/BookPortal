package tr.com.obss.jip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

@Entity
public class Admin extends BaseUser{
}
