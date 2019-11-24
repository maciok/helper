import {Timebox} from "./timebox.model";
import {HelpCategory} from "./help-category.model";

export interface HelpResponse {
  id: string;
  state: string;
  timeBox: string;
  category: string;
  description: string;
}

export interface Help {
  id: string;
  state: string;
  helpTimeBox: Timebox;
  category: HelpCategory;
  description: string;
}

export interface HelpRequest {
  helpTimeBox: Timebox;
  category: HelpCategory;
  description: string;
  localizationConsent?: boolean;
  localization?: string;
}
