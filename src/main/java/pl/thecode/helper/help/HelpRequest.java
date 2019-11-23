package pl.thecode.helper.help;

import lombok.Data;

@Data
class HelpRequest {
  private String localization;
  private boolean localizationConsent;
  private TimeBox helpTimeBox;
  private Category category;
  private String description;
}
