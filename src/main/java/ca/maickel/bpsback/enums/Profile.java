package ca.maickel.bpsback.enums;

public enum Profile {
  ADMIN(1, "ROLE_ADMIN"),
  REGULAR(2, "ROLE_REGULAR");

  private int code;
  private String description;

  private Profile(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static Profile toEnum(Integer code) {
    if (code == null) return null;

    for (Profile value : Profile.values()) {
      if (code.equals(value.getCode())) return value;
    }

    throw new IllegalArgumentException("Invalid profile role: " + code);
  }
}
