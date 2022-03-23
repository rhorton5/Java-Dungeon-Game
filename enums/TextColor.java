package enums;

public enum TextColor {
    RESET("\u001B[37m"),RED("\u001B[31m"),GREEN("\u001B[32m"),YELLOW("\u001B[33m");
    private final String colorID;
    private TextColor(String colorID){
        this.colorID = colorID;
    }
    public String getValue(){
        return this.colorID;
    }
    public String changeColor(String str){
        return this.colorID + str + RESET.getValue();
    }


}
