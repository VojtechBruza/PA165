package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private Category kitchen = new Category();
	private Category electro = new Category();
	private Product flashlight = new Product();
	private Product kitchenRobot = new Product();
	private Product plate = new Product();


	@BeforeClass
	void createData(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		electro.setName("Electro");
		em.persist(electro);
		kitchen.setName("Kitchen");
		em.persist(kitchen);
		flashlight.setName("Flashlight");
		flashlight.addCategory(electro);
		em.persist(flashlight);
		kitchenRobot.setName("Kitchen Robot");
		kitchenRobot.addCategory(electro);
		kitchenRobot.addCategory(kitchen);
		em.persist(kitchenRobot);
		plate.setName("Plate");
		plate.addCategory(kitchen);
		em.persist(plate);
		em.getTransaction().commit();
		em.close();
	}


	@Test
	public void testFlashlight(){
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, flashlight.getId());
		Assert.assertEquals(found.getCategories().size(), 1);
		Assert.assertEquals(found.getName(), "Flashlight");
		Assert.assertEquals(found.getCategories().iterator().next().getName(), "Electro");
		em.close();
	}

	@Test
	public void testPlate(){
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, plate.getId());
		Assert.assertEquals(found.getCategories().size(), 1);
		Assert.assertEquals(found.getName(), "Plate");
		Assert.assertEquals(found.getCategories().iterator().next().getName(), "Kitchen");
		em.close();
	}


	@Test
	public void testKitchenRobot(){
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, kitchenRobot.getId());
		Assert.assertEquals(found.getCategories().size(), 2);
		assertContainsCategoryWithName(found.getCategories(),"Electro");
		assertContainsCategoryWithName(found.getCategories(),"Kitchen");
		em.close();
	}



	@Test
	public void testCategoryKitchen(){
		EntityManager em = emf.createEntityManager();
		Category found = em.find(Category.class, kitchen.getId());
		Assert.assertEquals(found.getProducts().size(), 2);
		assertContainsProductWithName(found.getProducts(), "Kitchen Robot");
		assertContainsProductWithName(found.getProducts(), "Plate");
		em.close();
	}

	@Test
	public void testCategoryElectro(){
		EntityManager em = emf.createEntityManager();
		Category found = em.find(Category.class, electro.getId());
		Assert.assertEquals(found.getProducts().size(), 2);
		assertContainsProductWithName(found.getProducts(), "Flashlight");
		assertContainsProductWithName(found.getProducts(), "Kitchen Robot");
		em.close();
	}

	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
