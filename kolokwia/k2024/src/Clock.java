import java.time.LocalTime;

public abstract class Clock {
    LocalTime time;

    public void setCurrentTime(){
       this.time = LocalTime.now();
    }

    public void setTime(int hour, int minute, int sec){
        this.time = LocalTime.of(hour, minute, sec);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}
