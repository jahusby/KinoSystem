package no.kino.gui.customerContent;

import java.sql.Time;
import java.util.Date;

public class OrderConfirmation {
    private String movieName;
    private int movieTheaterNumber;
    private Date date;
    private Time time;
    private String ticketCode;
    private int rowNumber;
    private int seatNumber;

    public OrderConfirmation(String movieName, int movieTheaterNumber, Date date, Time time, String ticketCode, int rowNumber, int seatNumber){
        this.movieName = movieName;
        this.movieTheaterNumber = movieTheaterNumber;
        this.date = date;
        this.time = time;
        this.ticketCode = ticketCode;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;

    }

    private OrderConfirmation newOrderConfirmation(String movieName, int movieTheaterNumber, Date date, Time time, String ticketCode, int rowNumber, int seatNumber){
        return new OrderConfirmation(movieName, movieTheaterNumber, date, time, ticketCode, rowNumber, seatNumber);
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieTheaterNumber() {
        return movieTheaterNumber;
    }

    public void setMovieTheaterNumber(int movieTheaterNumber) {
        this.movieTheaterNumber = movieTheaterNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "OrderConfirmation{" +
                "movieName='" + movieName + '\'' +
                ", movieTheaterNumber=" + movieTheaterNumber +
                ", date=" + date +
                ", time=" + time +
                ", ticketCode='" + ticketCode + '\'' +
                ", rowNumber=" + rowNumber +
                ", seatNumber=" + seatNumber +
                '}';
    }

}
