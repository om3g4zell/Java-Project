package models.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import lib.MyLocalTime;
import models.Company;
import models.Employee;
import models.Manager;
import models.StandardDepartment;

public class ModelTest {

	Company c;
	
	@Test
	public void test() {
		c = Company.getInstance();
		assertNotNull(c);
		try{
			testCreationStandardDepartment();
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		testSerializationDeserialization();
	}
	
	public void testSerializationDeserialization() {
		
		c.serialize("test.ser");
		c = c.deserialize("test.ser");
		
		Employee e =  c.getEmployee("michel", "dupont");
		assertEquals(MyLocalTime.of(8, 0), e.getCheck(LocalDate.of(2017, 6, 5)).getBeginHourCheck());
		assertEquals(MyLocalTime.of(19, 59), e.getCheck(LocalDate.of(2017, 6, 5)).getEndHourCheck());
		
		assertEquals(120, e.getBonusTime());
		
	}
	
	public void testCreationStandardDepartment() {
		
		Manager m1 = c.createManager("jean", "kevin", MyLocalTime.of(8, 0), MyLocalTime.of(18, 0));
		
		StandardDepartment sd = c.createStandardDepartment("Departement Esport");
		sd.addEmployee(m1);
		c.setManager(sd, m1);
		testManager(m1, sd);
		testEmployee(sd);
		
		try{
			c.removeDepartment(sd);
		} catch(IllegalArgumentException e) {
			fail();
		}
		
		assertNull(m1.getDepartement());
		assertNull(m1.getStandardDepartmentManaged());
		assertEquals(2,c.getEmployees().size());
		assertTrue(c.getManagementDepartment().isHere(m1));
		
	}
	
	public void testCheckInCheckOut(Employee e) {
		
		e.check(LocalDateTime.of(2017, 6, 5, 8, 0));
		e.check(LocalDateTime.of(2017, 6, 5, 19, 59));
		
		assertEquals(MyLocalTime.of(8, 0), e.getCheck(LocalDate.of(2017, 6, 5)).getBeginHourCheck());
		assertEquals(MyLocalTime.of(19, 59), e.getCheck(LocalDate.of(2017, 6, 5)).getEndHourCheck());
		
		assertEquals(120, e.getBonusTime());
	}
	
	public void testManager(Manager m1, StandardDepartment sd) {
		
		assertEquals(m1, sd.getManager());
		assertEquals(1, c.getManagers().size());
		assertEquals(m1, c.getManagers().get(0));
		assertEquals(1, c.getEmployees().size());
		assertEquals(m1, c.getEmployees().get(0));
		assertEquals(sd, m1.getDepartement());
		assertEquals(sd, m1.getStandardDepartmentManaged());
		assertTrue(sd.isWorkingHere(m1));
		assertTrue(c.getManagementDepartment().isHere(m1));
		
		assertTrue(c.removeEmployee(m1));
		assertFalse(sd.isWorkingHere(m1));
		
		c.addManager(m1, sd);
		assertEquals(m1, sd.getManager());
		assertEquals(1, c.getManagers().size());
		assertEquals(m1, c.getManagers().get(0));
		assertEquals(1, c.getEmployees().size());
		assertEquals(m1, c.getEmployees().get(0));
		assertEquals(sd, m1.getDepartement());
		assertEquals(sd, m1.getStandardDepartmentManaged());
		assertTrue(sd.isWorkingHere(m1));
		assertTrue(c.getManagementDepartment().isHere(m1));
		
		try{
			c.addManager(m1, null);
			fail();
		}catch(IllegalArgumentException exception) {
			
		}
	}
	
	public void testEmployee(StandardDepartment sd) {
		Employee e = c.createEmployee("michel", "dupont", MyLocalTime.of(8, 0), MyLocalTime.of(18, 0), sd);
		
		c.createEmployee("jean", "bon",MyLocalTime.of(8, 0), MyLocalTime.of(18, 0), sd);
		
		assertEquals(3, c.getEmployees().size());
		assertEquals(e.getDepartement(), sd);
		
		Employee e2 = c.getEmployee("jean", "bon");
		assertEquals(e2.getDepartement(), sd);
		
		c.removeEmployee(e2);
		assertEquals(2, c.getEmployees().size());
		assertFalse(sd.isWorkingHere(e2));
		
		try{
			c.addEmployee(e2, null);
			fail();
		}catch(IllegalArgumentException exception) {
			
		}
		
		assertNull(c.getEmployee("prénom", "nom"));
		
		testCheckInCheckOut(e);
		
	}
	@Test
	public void testHour() {
		assertEquals(LocalTime.of(12, 45), MyLocalTime.of(12, 47));
		
		assertEquals(LocalTime.of(13, 00), MyLocalTime.of(12, 55));
		assertEquals(LocalTime.of(13, 0), MyLocalTime.of(13, 05));
		
	}

}
