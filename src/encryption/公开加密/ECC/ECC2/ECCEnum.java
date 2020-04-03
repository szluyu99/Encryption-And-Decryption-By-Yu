package encryption.公开加密.ECC.ECC2;

public enum ECCEnum {
 
    ALGORITHM("EC"),
    PROVIDER("BC"),
    PUBLIC_KEY("PUBLIC_KEY"),
    PRIVATE_KEY("PRIVATE_KEY");
 
 
 
    private String value;
 
    ECCEnum(String value) {
        this.value = value;
    }
 
    public String value() {
        return this.value;
    }
}