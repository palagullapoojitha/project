package projectjpa;

import org.jormunit.JPATest;
import org.jormunit.PrintDatabase;
import org.jormunit.TestConfiguration;
import org.jormunit.Transactional;
import org.jormunit.dataset.AfterDataSet;
import org.jormunit.dataset.BeforeDataSet;
import projectjpa.entity.Customer;
import org.junit.Assert;
import org.junit.Test;

@TestConfiguration(persistenceUnitName="default-pu")
public class SampleTest extends JPATest {
	
	private static final String DATASET_BEFORE = "dataset-before.xml";
	private static final String DATASET_AFTER = "dataset-after.xml";
	

	@Test
	@BeforeDataSet(fileName = DATASET_BEFORE)
	public void testFetchData() {
		Customer cust = getEntityManager().find(Customer.class, new Long(-1));
		Assert.assertNotNull(cust);
		Assert.assertEquals("Customer 1", cust.getName());
	}
	
	@Test
	@BeforeDataSet(fileName = DATASET_BEFORE)
	@AfterDataSet(fileName = DATASET_AFTER)
	public void testSaveDataManualTransaction() throws Exception {
		
		Customer cust = new Customer("Customer 2");
		
		try {
			beginTransaction();
			getEntityManager().persist(cust);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			throw e;
		}
		
	}
	
	@Test
	@Transactional
	@BeforeDataSet(fileName = DATASET_BEFORE)
	@AfterDataSet(fileName = DATASET_AFTER)
	@PrintDatabase
	public void testSaveDataAutomaticTransaction() {
		Customer cust = new Customer("Customer 2");
		getEntityManager().persist(cust);
	}
	

}
