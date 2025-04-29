import java.time.LocalTime;

import static java.lang.Math.abs;

public abstract class Clock {
    LocalTime time;
    private City city;

    public Clock(City city) {
        this.city = city;
    }

    public void setCity(City city) {
        City oldCity = this.city;
        int roznica = city.strefaCzasowa - oldCity.strefaCzasowa;
        LocalTime newTime = getTime().plusHours(roznica);
        this.time = newTime;
        this.city = city;

    }

        public void setCurrentTime(){
       this.time = LocalTime.now();
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(int hour, int minute, int sec){
        this.time = LocalTime.of(hour, minute, sec);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}
