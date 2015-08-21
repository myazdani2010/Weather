package example.com.exsqlite;

public class WeatherDetail {
    private int id;
    private String main;
    private String description;
    private String icon;
    private float tempMin;
    private float tempMax;
    private String  day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String  day) {
        this.day = day;
    }

    @Override
    public String toString(){
        return day + "    " + String.format("%.1f", tempMin) + "-" + String.format("%.1f", tempMax) + " ["+main+"]";
    }
}