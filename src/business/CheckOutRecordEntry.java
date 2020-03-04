package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutRecordEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;
    public CheckOutRecordEntry(LocalDate checkOutDate, LocalDate dueDate, BookCopy bookCopy) {
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
    }

    public CheckOutRecordEntry(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
