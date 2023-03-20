package App.Model;

public class Alert {
    private String status;
    private String information;
    private String ColorUI;
    private  String imgSrc;

    public Alert() {
    }

    public Alert(String status, String information, String colorUI, String imgSrc) {
        this.status = status;
        this.information = information;
        this.ColorUI = colorUI;
        this.imgSrc = imgSrc;
    }
    public Alert(String status, String colorUI, String imgSrc) {
        this.status = status;
        this.ColorUI = colorUI;
        this.imgSrc = imgSrc;
    }

    public String getStatus() {
        return status;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getColorUI() {
        return ColorUI;
    }

    public void setColorUI(String colorUI) {
        ColorUI = colorUI;
    }
}
