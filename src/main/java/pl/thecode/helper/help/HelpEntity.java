package pl.thecode.helper.help;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import pl.thecode.helper.user.Disabilities;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static pl.thecode.helper.help.HelpEntity.TABLE_NAME;


// @todo add creation date

@Entity
@Table(name = TABLE_NAME)
@Access(AccessType.FIELD)
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = PRIVATE)
class HelpEntity {

  public static final String TABLE_NAME = "help_request";

  private static final String DELIMITER = ",";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "help_request_gen")
  @SequenceGenerator(name = "help_request_gen", sequenceName = "help_request_seq", allocationSize = 1, initialValue = 100)
  private long id;

  private String needyId;

  private String needyDisabilities;
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

  HelpEntity(
    String needyId, List<Disabilities> disabilities, String localization, TimeBox timeBox, Category category, String description
  ) {
    this.needyId = needyId;
    this.needyDisabilities = disabilities.stream().map(Disabilities::name).collect(Collectors.joining(DELIMITER));
    this.localization = localization;
    this.timeBox = timeBox;
    this.category = category;
    this.description = description;
    this.state = State.NEW;
  }

  List<Disabilities> getNeedyDisabilities() {
    return Stream.of(needyDisabilities.split(DELIMITER))
                 .filter(not(String::isEmpty))
                 .map(Disabilities::valueOf)
                 .collect(Collectors.toUnmodifiableList());

  }

  HelpDto createDto() {
    var needyDisabilities = getNeedyDisabilities().stream()
                                                  .map(Disabilities::createDescriptive)
                                                  .collect(Collectors.toList());

    return new HelpDto(
      id, state, timeBox, category, description, localization, needyDisabilities
    );
  }

  void close() {
    this.state = State.CLOSED;
  }

  void assignHelper(String uuid) {
    this.helperId = uuid;
    this.state = State.RESERVED;
  }
}
