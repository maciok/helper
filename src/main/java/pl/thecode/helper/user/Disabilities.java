package pl.thecode.helper.user;

public enum Disabilities {
  PHYSICAL_DISABILITY,
  VISION_IMPAIRMENT,
  HEARING_IMPAIRMENT,
  CARDIOVASCULAR_DISEASES,
  DIABETES,
  PREGNANCY,
  ALLERGIES,
  SPINAL_DISEASES,
  ;

  public DescriptiveDisability createDescriptive() {
    return DescriptiveDisability.from(this);
  }
}
