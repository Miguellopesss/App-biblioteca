package pt.ipleiria.estg.dei.ei.esoft.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
    private LocalDate date;

    public Data(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }
    public Data(String dateString) {
        this.date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public LocalDate getDate() {
        return date;
    }
    public Data addDays(int days) {
        LocalDate newDate = this.date.plusDays(days);
        return new Data(newDate.getYear(), newDate.getMonthValue(), newDate.getDayOfMonth());
    }
    public static Data today() {
        return new Data(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }
    public boolean isBefore(Data other) {
        return this.date.isBefore(other.getDate());
    }

    @Override
    public String toString() {
        return "" +
                date
                ;
    }
}
