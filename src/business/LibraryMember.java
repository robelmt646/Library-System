package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckOutRecord checkOutRecord;

	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.checkOutRecord = new CheckOutRecord();
	}

	public String getMemberId() {
		return memberId;
	}

	public CheckOutRecord getCheckOutRecord() {
		if (this.checkOutRecord == null) {
			this.checkOutRecord = new CheckOutRecord();
		}
		return this.checkOutRecord;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
