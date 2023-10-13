package util;


public enum LookupType {
  PHONE,
  EMAIL,
  THREEMAID,
  INVALID;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }

  public static LookupType getByString(String id) {
    return switch(id) {
      case "phone" -> LookupType.PHONE;
      case "email" -> LookupType.EMAIL;
      case "threemaid" -> LookupType.THREEMAID;
      default -> LookupType.INVALID;
    };
  }

  public static LookupType getByPattern(String id) {
    LookupType type = LookupType.INVALID;
    if(id.lastIndexOf('@') < id.lastIndexOf('.')){
       type = LookupType.EMAIL;
     }else if(id.matches("\\d{11}")) {
       type = LookupType.PHONE;
     }else {
       type = LookupType.THREEMAID;
     }
    return type;
  }
}
