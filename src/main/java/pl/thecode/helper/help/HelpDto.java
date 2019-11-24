package pl.thecode.helper.help;

import java.util.List;
import lombok.Value;
import pl.thecode.helper.user.Disabilities;

@Value
class HelpDto {

  private long id;
  private State state;
  private TimeBox timeBox;
  private Category category;
  private String description;
  private String localization;
  // private LocalDateTime creationDate;

  private List<Disabilities> disabilities;


}
