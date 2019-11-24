package pl.thecode.helper.user;

import static pl.thecode.helper.user.Disabilities.PHYSICAL_DISABILITY;
import static pl.thecode.helper.user.Disabilities.ALLERGIES;
import static pl.thecode.helper.user.Disabilities.CARDIOVASCULAR_DISEASES;
import static pl.thecode.helper.user.Disabilities.DIABETES;
import static pl.thecode.helper.user.Disabilities.HEARING_IMPAIRMENT;
import static pl.thecode.helper.user.Disabilities.PREGNANCY;
import static pl.thecode.helper.user.Disabilities.SPINAL_DISEASES;
import static pl.thecode.helper.user.Disabilities.VISION_IMPAIRMENT;

import java.util.Map;
import lombok.Value;

@Value
public class DescriptiveDisability {

  private static final Map<Disabilities, DescriptiveDisability> disabilityToDescription = Map.of(
    VISION_IMPAIRMENT, new DescriptiveDisability(VISION_IMPAIRMENT, "Trudność w poruszaniu się, czytaniu.", "Osoba mająca problemy ze wzrokiem może potrzebować pomocy w poruszaniu się, transporcie. Osoba niepełnosprawna sama wskaże Ci, jak możesz jej najlepiej pomóc."),
    HEARING_IMPAIRMENT, new DescriptiveDisability(HEARING_IMPAIRMENT, "Trudność w poruszaniu się, komunikowaniu się np. w urzędzie czy w sklepie.", "Pomoc osobie z problemami ze słuchem będzie wymagała od Ciebie dużo pozytywnego nastawienia i trochę cierpliwości.  Mile widziana będzie znajomość języka migowego, ale nie jest ona wymagana."),
    CARDIOVASCULAR_DISEASES, new DescriptiveDisability(CARDIOVASCULAR_DISEASES, "Trudność polega na pojawieniu się niepokojących objawów", "Osoba cierpiąca na choroby układu krążenia prawdopodobnie przyjmuje leki powodujące obniżenie ciśnienia tętniczego krwi. Upewnij się, że je przyjęła lub pomóż jej je przyjąć. Możesz otworzyć okno, by poprawić przepływ powietrza w pomieszczeniu. Jeśli objawy nie ustąpią – wezwij lekarza lub pogotowie(112)."),
    DIABETES, new DescriptiveDisability(DIABETES, "Osoba chora na cukrzycę może niespodziewanie zasłabnąć, może spać jej poziom glukozy we krwi. Jeśli jest to osoba samotna, może w trosce o swoje zdrowie potrzebować obecności osoby, która będzie przy niej podczas powrotu poziomu glukozy do normy. Objawy niedocukrzenia: pocenie się, drżenia rąk i nóg, zawroty głowy, niepokój, pobudzenie, bladość skóry.", "Osobie z cukrzycą należy pomóc w sprawdzeniu poziomu glukozy we krwi. Warto podać jej coś słodkiego do picia: sok, colę, a nawet wodę z cukrem. Koniecznie podaj tej osobie coś, co błyskawicznie podniesie cukier. Poczekaj, aż objawy niedocukrzenia ustąpią. Jeśli stan ulegnie pogorszeniu – wezwij lekarza lub pogotowie (112)."),
    PREGNANCY, new DescriptiveDisability(PREGNANCY, "Kobieta w ciąży, szczególnie w zaawansowanej, często ma problem z poruszaniem się po mieście. Mogą też niepokoić się o swoje zdrowie i zdrowie dziecka.", "Kobiecie w ciąży można pomóc poprzez wsparcie w przeniesieniu zakupów, uspokojenie jej.  Wspieraj, zagaduj, pomóż się opanować. Jeśli kobieta jest w zaawansowanej ciąży – wezwij lekarza lub pogotowie (112)."),
    ALLERGIES, new DescriptiveDisability(ALLERGIES, "Alergia to nadwrażliwość i często nadmierna reakcja na alergen występujący w otoczeniu lub alergen pokarmowy. Mogą występować duszności, bóle głowy, bóle brzucha, wymioty, uczucie kołatania w klatce piersiowej.", "Aby pomóc alergikowi upewnij się na jaki alergen jest wrażliwy i czy przyjmuje leki. Pomóż mu w przyjęciu leków i wspieraj na duchu. Jeśli objawy nie ustąpią – wezwij lekarza lub pogotowie (112)"),
    SPINAL_DISEASES, new DescriptiveDisability(SPINAL_DISEASES, "Choroby kręgosłupa wiążą się z ograniczeniem w poruszaniu się.", "Osoby cierpiące na problemy z kręgosłupem będą potrzebowały od Ciebie wsparcia w poruszaniu się. Okaż im między innymi wsparcie fizyczne."),
    PHYSICAL_DISABILITY, new DescriptiveDisability(PHYSICAL_DISABILITY, "Trudność w poruszaniu się, pokonywaniu przeszkód architektonicznych.", "Pomoc takiej osobie będzie wymagała od Ciebie zaangażowania głównie siły fizycznej. Czasem wystarczy, że przytrzymasz czyjąś rękę przy schodzeniu po schodach lub przeprowadzisz potrzebującego przez miasto. Osoba z niepełnosprawnością sama wskaże Ci, z czym ma problem.")
  );

  private Disabilities disability;
  private String description;
  private String helpInstruction;

  static DescriptiveDisability from(Disabilities disability) {
    return disabilityToDescription.get(disability);
  }
}
