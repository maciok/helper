package pl.thecode.helper.help;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static pl.thecode.helper.help.HelpEntity.TABLE_NAME;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = TABLE_NAME)
@Access(AccessType.FIELD)
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = PRIVATE)
class HelpEntity {

  public static final String TABLE_NAME = "help_request";

  HelpEntity(
    String needyId, String localization, TimeBox timeBox, Category category, String description
  ) {
    this.needyId = needyId;
    this.localization = localization;
    this.timeBox = timeBox;
    this.category = category;
    this.description = description;
    this.state = State.NEW;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "help_request_gen")
  @SequenceGenerator(name = "help_request_gen", sequenceName = "help_request_seq", allocationSize = 1, initialValue = 100)
  private long id;

  private String needyId;
  private String helperId;
  
  private String localization;

  @Enumerated(STRING)
  @ColumnTransformer(read = "time_box::text", write = "?::TimeBox")
  @Column(name = "timeBox")
  private TimeBox timeBox;

  @Enumerated(STRING)
  @ColumnTransformer(read = "category::text", write = "?::Category")
  @Column(name = "category")
  private Category category;
  private String description;
  
  @Enumerated(STRING)
  @ColumnTransformer(read = "state::text", write = "?::State")
  @Column(name = "state")
  private State state;

}
