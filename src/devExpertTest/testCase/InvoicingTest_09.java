package devExpertTest.testCase;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import devExpertTest.model.*;


@RunWith(Parameterized.class)
public class InvoicingTest_09 {

	Address billingAddress = null; 
	Address shippingAddress = null;      
	Customer customer = null; 
	Book book = null; 
	Invoice invoice = null;
	
	private List<Object> testObjects;

	private double _quantity;
	private double _price;
	private double _discount;
	private double _expected;
	
	
	@Parameters(name = "{index}: (Quantity {0} * Price {1}) * (Discount{2}/100) = {3}")
	public static Collection<Object[]> generateData()
	{
		return Arrays.asList(new Object[][] {
							  { 5, 10, 10, 45 },
							  { 10, 20, 20, 160 },
							  { 2, 100, 50, 100 }});
	}
	
	private void printThreadId(String method){
		System.out.println("in Method: " + method + " Current Thread ID: " + Thread.currentThread().getId());
	}
	
	public InvoicingTest_09(double quantity, double price, double discount, double expected)
	{
		this._quantity = quantity;
		this._price = price;
		this._discount = discount;
		this._expected = expected;
		printThreadId("constructor");
	}
	
	@Before
	public void setUp() throws Exception {      
		testObjects = new ArrayList<Object>();
		
		// Set up Fixture                
	    customer = createCustomer();  
	    printThreadId("setup");
	}
	
	@After
	public void tearDown() {
		printThreadId("tearDown");
		for(Object item : testObjects) {
			deleteObject(item);         
		}   
	}
	
	@Test
	public void testAddItemQuantity(){      
		printThreadId("testAddItemQuantity");
   
	  // Set up Fixture 
	  book = createBook(this._price);
	  invoice = createInvoice(customer);
	    
      // Exercise SUT
      invoice.addItemQuantity(book, this._quantity, this._discount);

      // Verify outcome
      assertEquals("number of items" , 1, invoice.getItems().size());
      
      DocumentItem docItemExpected = new DocumentItem(invoice, book, this._quantity, this._discount);
      DocumentItem docItemActual = invoice.getItems().get(0);
      
      assertDocumentItemEquals(docItemExpected, docItemActual);
	}
	
	@Test
	public void testRemoveItemQuantity(){    
		printThreadId("testRemoveItemQuantity");
   
	  // Set up Fixture 
	  book = createBook(this._price);
	  invoice = createInvoice(customer);
		  
      // Exercise SUT
      invoice.addItemQuantity(book, this._quantity, this._discount);
      invoice.removeItem(0);

      // Verify outcome
      assertEquals("number of items" , 0, invoice.getItems().size());
	}
	
	
	private Address createBillingAddress() {
		billingAddress = new Address("Heidelbergerstrasse 16", "69190", "Walldorf", "BW", "Germany");      
		registerTestObject(billingAddress);
		return billingAddress;
	}
	
	private Address createShippingAddress() {
		shippingAddress = new Address("Hauptstrasse 5", "69190", "Walldorf", "BW", "Germany");
		registerTestObject(shippingAddress);
		return shippingAddress;
	}
	
	private Customer createCustomer() {
		customer = new Customer(99, "Astor Books Store",  createBillingAddress(), createShippingAddress());
		registerTestObject(customer);
		return customer;
	}
	
	private Book createBook(double price) {
		book = new Book("978-0321146533", "Test Driven Development: By Example", price);
		registerTestObject(book);
		return book;
	}
	
	private Invoice createInvoice(Customer customer) {
		invoice = new Invoice(createCustomer());
		registerTestObject(invoice);
		return invoice;
	}
	
	private void deleteObject(Object object) {
		object = null;
	}
	
	private void registerTestObject(Object testObject) {      
		testObjects.add(testObject);   
	}
	
	private void assertDocumentItemEquals(DocumentItem expected, DocumentItem actual) {
		assertEquals("book", expected.getItem(), actual.getItem());
		assertEquals("quantity", expected.getQuantity(), actual.getQuantity(), 0);
		assertEquals("discount", expected.getDiscount(), actual.getDiscount(), 0);
		assertEquals("unit price", expected.getItem().getPrice(), actual.getItem().getPrice(), 0);
		assertEquals("extended price", expected.getExtendedPrice(), actual.getExtendedPrice(), 0);
	}
	
}
