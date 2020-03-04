package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CheckOutRecordEntry> entryList = new ArrayList<>();

    public List<CheckOutRecordEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<CheckOutRecordEntry> list) {
        this.entryList = list;
    }

    public void addCheckOutRecord(LocalDate checkOutDate, LocalDate dueDate, BookCopy bookCopy) {
        this.entryList.add(new CheckOutRecordEntry(checkOutDate, dueDate, bookCopy));
    }
    
    public void RemoveRecord(LibraryMember a) {
        
        	this.entryList.remove(entryList.size()-1);
        
    }

}
