package demo.upload.rest.file;

import com.opencsv.bean.CsvBindByName;

public class TransactionCsvFileDataLine {

    @CsvBindByName(column = "nadawca")
    private String sender;

    @CsvBindByName(column = "odbiorca")
    private String receiver;

    @CsvBindByName(column = "data")
    private String date;

    @CsvBindByName(column = "kwota")
    private String amount;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
