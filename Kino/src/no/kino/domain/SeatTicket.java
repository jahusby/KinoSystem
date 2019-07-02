package no.kino.domain;

public class SeatTicket {
    private int rowNr;
    private int seatNr;
    private int moviePlaceNr;
    private String ticketCode;

    public SeatTicket(int rowNr, int seatNr, int moviePlaceNr, String ticketCode) {
        this.rowNr = rowNr;
        this.seatNr = seatNr;
        this.moviePlaceNr = moviePlaceNr;
        this.ticketCode = ticketCode;
    }

    private SeatTicket newTicketplace(int rowNr, int seatNr, int moviePlaceNr, String ticketCode){
        return new SeatTicket(rowNr, seatNr, moviePlaceNr, ticketCode);
    }

    public int getRowNr() {
        return rowNr;
    }

    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public void setSeatNr(int seatNr) {
        this.seatNr = seatNr;
    }

    public int getMoviePlaceNr() {
        return moviePlaceNr;
    }

    public void setMoviePlaceNr(int moviePlaceNr) {
        this.moviePlaceNr = moviePlaceNr;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;

    }

    @Override
    public String toString() {
        return "ticketPlace{" +
                "rowNr=" + rowNr +
                ", seatNr=" + seatNr +
                ", moviePlaceNr=" + moviePlaceNr +
                ", ticketCode='" + ticketCode + '\'' +
                '}';
    }
}
