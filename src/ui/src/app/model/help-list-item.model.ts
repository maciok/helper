import {Timebox} from "./timebox.model";
import {HelpCategory} from "./help-category.model";

export interface HelpListItem {
  id: string;
  state: string;
  timeBox: Timebox;
  category: HelpCategory;
  description: string;
  localization: string;
  open?: boolean;
  chatId?: string;
}
