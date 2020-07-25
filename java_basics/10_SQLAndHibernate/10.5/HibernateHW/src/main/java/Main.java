import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

//        hw10_5_1(session);
//        criteriaTest(session);
//        hqlTest(session);

        anotheLookForHw10_15(session);
//        hw10_15(session);


        sessionFactory.close();
    }

    static void hw10_5_1(Session session){
        //Subscriptions
        Criteria subCriteria = session.createCriteria(Subscription.class);
        //subCriteria.add(Restrictions.eq("student.name", "Фуриков Эрнст"));
        List<Subscription> subscriptions = subCriteria.list();
        subscriptions.forEach(System.out::println);

        //Purchaselist
        Criteria purCriteria = session.createCriteria(Purchase.class);
        purCriteria.add(Restrictions.eq("id.courseName","Веб-разработчик c 0 до PRO"));
        List<Purchase> purchases = purCriteria.list();
        purchases.forEach(System.out::println);

        //Courses
        Criteria courseCriteria = session.createCriteria(Course.class);
        courseCriteria.add(Restrictions.eq("type", CourseType.PROGRAMMING));
        List<Course> programmingList = courseCriteria.list();
        programmingList.forEach(System.out::println);

        //Teachers
        Teacher teacher = session.get(Teacher.class, 1);
        System.out.println(teacher);

        //Students
        Student student = session.get(Student.class,1);
        System.out.println(student);
    }

    static void criteriaTest(Session session){
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Course> qCourse = builder.createQuery(Course.class);
        Root<Course> rCourse = qCourse.from(Course.class);
        qCourse.select(rCourse).where(builder.lessThan(rCourse.get("price"), 10000));

        List<Course> courseList = session.createQuery(qCourse).getResultList();
        System.out.println(courseList);


        CriteriaQuery<Subscription> qSubscription = builder.createQuery(Subscription.class);
        Root<Subscription> rSubscription = qSubscription.from(Subscription.class);
        qSubscription.select(rSubscription).where(builder.lessThan(rSubscription.get("subscriptionDate"), new Date()));

        List<Subscription> subscriptionList = session.createQuery(qSubscription).getResultList();
        System.out.println(subscriptionList);
    }

    static void hqlTest(Session session){
        String hql = "FROM " + Course.class.getSimpleName() + " WHERE price > 121000";
        List<Course> courseList = session.createQuery(hql).getResultList();
        System.out.println(courseList);
    }

    static void hw10_15(Session session){
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Purchase> purchaseCriteriaQuery = builder.createQuery(Purchase.class);
        Root<Purchase> purchaseRoot = purchaseCriteriaQuery.from(Purchase.class);
        purchaseCriteriaQuery.select(purchaseRoot);

        CriteriaQuery<Student> studentCriteriaQuery = builder.createQuery(Student.class);
        Root<Student> studentRoot = studentCriteriaQuery.from(Student.class);

        CriteriaQuery<Course> courseCriteriaQuery = builder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        List<Purchase> purchaseList = session.createQuery(purchaseCriteriaQuery).getResultList();
        purchaseList.forEach(pl -> {
            String studentName = pl.getId().getStudentName();
            String courseName = pl.getId().getCourseName();

            studentCriteriaQuery.select(studentRoot).where(builder.equal(studentRoot.get("name"), studentName));
            Student student = session.createQuery(studentCriteriaQuery).getSingleResult();

            courseCriteriaQuery.select(courseRoot).where(builder.equal(courseRoot.get("name"), courseName));
            Course course = session.createQuery(courseCriteriaQuery).getSingleResult();

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList(student, course, pl.getId().getPrice(), pl.getId().getSubscriptionDate());
            session.saveOrUpdate(linkedPurchaseList);
        });
        transaction.commit();
    }

    static void anotheLookForHw10_15(Session session){
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT " +

                "s.id AS studentId, " +
                "c.id AS courseId, " +
                "pl.id.price AS price, " +
                "pl.id.subscriptionDate AS subDate " +

                "FROM Purchase pl LEFT JOIN Student s ON pl.id.studentName=s.name " +
                "LEFT JOIN Course c ON pl.id.courseName=c.name";

        Query<LinkedPurchaseListMapper> query = session.createQuery(sql);
        query.setResultTransformer(Transformers.aliasToBean(LinkedPurchaseListMapper.class));

        List<LinkedPurchaseList> linkedPurchaseLists = new ArrayList<>();
        query.list().forEach(lplm -> {
            //В LinkedPurchaseList встроены сущности, а не внешние ключи...
            //TODO: Как в таком случае создавать новые зависимые объекты?

            Student student = session.getReference(Student.class, lplm.getStudentId());
            Course course = session.getReference(Course.class, lplm.getCourseId());

            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList(student, course, lplm.getPrice(), lplm.getSubDate());
            linkedPurchaseLists.add(linkedPurchaseList);
        });

        linkedPurchaseLists.forEach(session::saveOrUpdate);
        transaction.commit();
    }
}
