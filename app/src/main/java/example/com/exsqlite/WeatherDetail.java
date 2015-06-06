package example.com.exsqlite;

/**
 * Created by Mohammad on 6/4/2015.
 */
public class WeatherDetail {
    private int id;
    private String main;
    private String description;
    private String icon;
    private int tempMin;
    private int tempMax;
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

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
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
        return day + "    " + tempMin + "-" + tempMax + " ["+main+"]";
    }
}
