package cz.fi.muni.pa165.dao;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private ProductDao productDao;

	@PersistenceContext
	private EntityManager em;

	// @Test(expectedExceptions=ConstraintViolationException.class)
	public void nameNotNull() {
		Category cat = new Category();
		cat.setName(null);
		categoryDao.create(cat);
	}

	// @Test(expectedExceptions=PersistenceException.class)
	public void nameUnique() {
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);
		cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);

	}

	// @Test
	public void createCategory() {
		Category cat = new Category();
		cat.setName("Electronics");
		categoryDao.create(cat);

		em.flush();
		em.clear();

		Category created = categoryDao.findById(cat.getId());

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> p = cq.from(Product.class);
		cq.select(p).where(cb.equal(p.get("name"), "Guitar"));
		TypedQuery<Product> tq = em.createQuery(cq);
		tq.getResultList();

		Assert.assertEquals(created, cat);
	}

	@Test
	public void productsInCategory() {
		Category categoryElectro = new Category();
		categoryElectro.setName("Electronics");
		categoryDao.create(categoryElectro);

		Product p = new Product();
		p.setName("TV");

		productDao.create(p);
		p.addCategory(categoryElectro);

		p = new Product();
		p.setName("PHONE");

		productDao.create(p);
		p.addCategory(categoryElectro);

		em.flush();
		em.clear();

		/** Solution using Criteria API in Java EE **/
		// CriteriaBuilder cb = em.getCriteriaBuilder();
		// CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		// Root<Category> c = cq.from(Category.class);
		// Join<Category, Product> product
		// =c.join("products",javax.persistence.criteria.JoinType.LEFT);
		// c.fetch("products");
		// cq.select(c).where(cb.equal(product.get("name"),"PHONE"));
		// TypedQuery<Category> tq= em.createQuery(cq);
		// Category foundc = tq.getSingleResult();

		/** Solution using uncorrelated subquery **/
		// DetachedCriteria prod = DetachedCriteria.forClass(Product.class, "p")
		// .add( Restrictions.conjunction(Property.forName("p.name").eq("TV")) )
		// .setProjection( Projections.property("p.id"));
		//
		// Session sess = (Session) em.getDelegate();
		// Criteria c = sess.createCriteria(Category.class,"cat");
		// c.createCriteria("cat.products", "prod", JoinType.LEFT_OUTER_JOIN);
		// c.add(Subqueries.propertyIn("cat.id",prod));
		// Category foundc = (Category) c.list().get(0);

		/** Solution using JPQL **/
//		 Category foundc = em.createQuery("select c from Category c left join c.products prod join fetch c.products where prod.name='TV' ",
//		 Category.class).getResultList().get(0);

		/** Solution by using LAZY fetch **/
		Session sess = (Session) em.getDelegate();
		Criteria c = sess.createCriteria(Category.class);
		Criteria products = c.createCriteria("products", "products", JoinType.LEFT_OUTER_JOIN);
		products.add(Restrictions.eq("name", "TV"));
		Category foundc = (Category) products.list().get(0);

		
		Assert.assertEquals(foundc.getProducts().size(), 2);
	}
}
