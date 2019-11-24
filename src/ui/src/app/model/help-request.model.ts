import { Timebox } from "./timebox.model";
import { HelpCategory } from "./help-category.model";

export interface HelpRequest {
  helpTimeBox: Timebox;
  category: HelpCategory;
  description: string;
  localizationConsent: boolean;
  localization: string;
}
