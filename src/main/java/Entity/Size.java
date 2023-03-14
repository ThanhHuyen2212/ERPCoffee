package Entity;

public class Size {
    private String sign;
    private String description;

    public Size() {
    }

    public Size(String sign, String description) {
        this.sign = sign;
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
