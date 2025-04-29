public class DigitalClock extends Clock {
    enum Type{
        h24,
        h12
    }
    private Type type;

    public DigitalClock(City city, Type type) {
        super(city);
        this.type = type;
    }

    @Override
    public String toString() {
        if(type == Type.h12){
            int h = time.getHour();
            if(h>12){
                return String.format("%02d:%02d:%02d PM", h-12, time.getMinute(), time.getSecond());
            }
            else if(h == 12){
                return String.format("%02d:%02d:%02d PM", h, time.getMinute(), time.getSecond());
            }
            else if(h == 0){
                return String.format("%02d:%02d:%02d AM", h+12, time.getMinute(), time.getSecond());
            }
            else{
                return String.format("%02d:%02d:%02d AM", h, time.getMinute(), time.getSecond());
            }

        }
        return super.toString();
    }
}
