package business;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	private User currentUser = null;
	private DataAccess da = new DataAccessFacade();
	private HashMap<String, User> usersMap;
	private HashMap<String, Book> booksMap;
	private HashMap<String, LibraryMember> membersMap;

	SystemController() {
		usersMap = da.readUserMap();
		booksMap = da.readBooksMap();
		membersMap = da.readMemberMap();
	};

	@Override
	public void login(String id, String password) throws LoginException {
		if (!usersMap.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = usersMap.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}

		currentUser = usersMap.get(id);
	}

	@Override
	 public String generateMemberId() {
	  int rand = this.membersMap.size() + 1;
	  return "LIB-" + rand;
	 }
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public HashMap<String, Book> getBooksMap() {
		return booksMap;
	}

	@Override
	public HashMap<String, Book> searchBooks(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> retval = da.findBooksMap(isbn);
		return retval;
	}

	@Override
	public void validateCheckOutForm(String memberId, String isbn) throws ValidationException {
		if (!this.membersMap.containsKey(memberId)) {
			throw new ValidationException("Member does not exist!");
		}

		if (!this.booksMap.containsKey(isbn)) {
			throw new ValidationException("ISBN does not exist!");
		}

		if (this.booksMap.get(isbn).getNextAvailableCopy() == null) {
			throw new ValidationException(("No available copy for this book!"));
		}
	}

	@Override
	public LibraryMember getLibraryMember(String memberId) {
		return membersMap.get(memberId);
	}

	@Override
	public void checkOut(String memberId, String isbn) throws ValidationException {
		validateCheckOutForm(memberId, isbn);

		Book book = this.booksMap.get(isbn);
		BookCopy bookCopy = book.getNextAvailableCopy();
		bookCopy.changeAvailability();

		LibraryMember member = this.membersMap.get(memberId);
		member.getCheckOutRecord().addCheckOutRecord(LocalDate.now(),
				LocalDate.now().plusDays(book.getMaxCheckoutLength()), bookCopy);

		da.saveNewMember(this.membersMap.get(memberId));
		da.saveBook(book);
	}
	
	@Override
	public void checkIN(String memberId, String isbn) throws ValidationException {
		validateCheckOutForm(memberId, isbn);
				
		Book book = this.booksMap.get(isbn);
		BookCopy bookCopy = book.getNextAvailableCopy();
		bookCopy.increaseAvailability();
		

		LibraryMember member = this.membersMap.get(memberId);
		member.getCheckOutRecord().RemoveRecord(member);

	
	}

	@Override
	public void updateBooksMap() {
		booksMap = da.readBooksMap();
	};

	@Override
	public void addACopy(Book book) {
		book.addCopy();
		da = new DataAccessFacade();
		da.saveNewCopy(book);
	}

	@Override
	public void addBook(String isbn, String title, String maxCheckoutLength, List<Author> authors, String copyNum)
			throws ValidationException {
		if (isbn == null || "".equals(isbn)) {
			throw new ValidationException("ISBN is empty");
		}

		if (title == null || "".equals(title)) {
			throw new ValidationException("Title is empty");
		}

		

		int l = 0;
		if (maxCheckoutLength == null || "".equals(maxCheckoutLength)) {
			throw new ValidationException("MaxCheckoutLength is invalid");
		} else {
			try {
				l = Integer.parseInt(maxCheckoutLength);
				if (l == 0) {
					throw new ValidationException("MaxCheckoutLength is invalid");
				} else if (l > 21) {
					throw new ValidationException("MaxCheckoutLength can not be Over 21 days");
				}
	
			} catch (Exception e) {
				throw new ValidationException("MaxCheckoutLength is invalid");
			}
		}

		int n = 0;
		if (copyNum == null || "".equals(copyNum)) {
			throw new ValidationException("Number of copies is invalid");
		} else {
			try {
				n = Integer.parseInt(copyNum);
				if (n == 0) {
					throw new ValidationException("Number of copies is invalid");
				}
			} catch (Exception e) {
				throw new ValidationException("Number of copies is invalid");
			}
		}

		if (booksMap.containsKey(isbn)) {
			throw new ValidationException("ISBN is already exist");
		}

		Book book = new Book(isbn, title, l, authors);
		while (book.getNumCopies() < n) {
			book.addCopy();
		}

		da.saveBook(book);
		booksMap.put(book.getIsbn(), book);
	}

	@Override
	public Author createAuthor(String f, String l, String t, String bio, String street, String city, String state,
			String zip) throws ValidationException {
		if (isEmpty(f)) {
			throw new ValidationException("FirstName is empty");
		}

		if (isEmpty(l)) {
			throw new ValidationException("LastName is empty");
		}

		Address address = null;
		if (!isEmpty(street) || !isEmpty(city) || !isEmpty(state) || !isEmpty(zip)) {
			address = new Address(street, city, state, zip);
		}
		return new Author(f, l, t, address, bio);
	}

	private boolean isEmpty(String t) {
		return (t == null || "".equals(t));
	}

	@Override
	public void printCheckOutRecord(LibraryMember member, CheckOutRecordEntry entry) {
		if (member == null || entry == null) {
			return;
		}

		BookCopy bookCopy = entry.getBookCopy();
		Book book = bookCopy.getBook();

		Map<String, String> m = new HashMap<String, String>();
		m.put("MemberId", member.getMemberId());
		m.put("MemberName", member.getFirstName() + " " + member.getLastName());
		m.put("ISBN", book.getIsbn());
		m.put("Title", book.getTitle());
		m.put("CopyNumber", String.valueOf(bookCopy.getCopyNum()));
		m.put("CheckOutDate", entry.getCheckOutDate().format(DateTimeFormatter.BASIC_ISO_DATE));
		m.put("DueDate", entry.getDueDate().format(DateTimeFormatter.BASIC_ISO_DATE));

		outputConsoleWithFixLength(m);
	}

	private void outputConsoleWithFixLength(Map<String, String> map) {
		final StringBuffer head = new StringBuffer();
		final StringBuffer value = new StringBuffer();
		map.forEach((r, v) -> {
			int len = (Math.max(r.length(), v.length()) + 5);
			head.append(String.format("%1$" + len + "s", r));
			value.append(String.format("%1$" + len + "s", v));
		});

		System.out.println(head);
		System.out.println(value);
	}

	@Override
	public void validateAddMemberForm(String memberId, String firstName, String lastName, String telephone,
			String street, String city, String state, String zip) throws ValidationException {

		if (this.membersMap.containsKey(memberId)) {
			throw new ValidationException("Member ID already exists!");
		}

		if (memberId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || telephone.isEmpty() || street.isEmpty()
				|| city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
			throw new ValidationException("All fields are required!");
		}

	}

	@Override
	public void addMember(String memberId, String firstName, String lastName, String telephone, String street,
			String city, String state, String zip) {
		Address address = new Address(street, city, state, zip);
		LibraryMember member = new LibraryMember(memberId, firstName, lastName, telephone, address);
		da.saveNewMember(member);
		updateMembersMap();
	}

	@Override
	public void updateMembersMap() {
		membersMap = da.readMemberMap();
	};

	@Override
	public HashMap<String, LibraryMember> getMembersMap() {
		return membersMap;
	}

	
	@Override
	public void validateOverdueForm(String isbn) throws ValidationException {
		if (!this.booksMap.containsKey(isbn)) {
			throw new ValidationException("Book does not exist!");
		}
	}

	@Override
	 public void validateUpdateMemberForm(String memberId, String firstName, String lastName, String telephone,
	   String street, String city, String state, String zip) throws ValidationException {

	  if (!this.membersMap.containsKey(memberId)) {
	   throw new ValidationException("Member ID does not exists in database!");
	  }

	  if (memberId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || telephone.isEmpty() || street.isEmpty()
	    || city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
	   throw new ValidationException("All fields are required!");
	  }

	 }

	@Override
	 public void updateBook(String isbn, String title, String maxCheckoutLength, List<Author> authors, String copyNum)
	   throws ValidationException {
	  if (isbn == null || "".equals(isbn)) {
	   throw new ValidationException("ISBN is empty");
	  }

	  if (title == null || "".equals(title)) {
	   throw new ValidationException("Title is empty");
	  }

	  int l = 0;
	  if (maxCheckoutLength == null || "".equals(maxCheckoutLength)) {
	   throw new ValidationException("MaxCheckoutLength is invalid");
	  } else {
	   try {
	    l = Integer.parseInt(maxCheckoutLength);
	    if (l == 0) {
	     throw new ValidationException("MaxCheckoutLength is invalid");
	    } else if (l > 21) {
	     throw new ValidationException("MaxCheckoutLength can not be Over 21 days");
	    }

	   } catch (Exception e) {
	    throw new ValidationException("MaxCheckoutLength is invalid");
	   }
	  }

	  int n = 0;


	  Book book = new Book(isbn, title, l, authors);

	  booksMap.put(book.getIsbn(), book);
	 }

}