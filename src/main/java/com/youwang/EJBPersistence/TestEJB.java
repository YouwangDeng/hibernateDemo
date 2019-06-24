package com.youwang.EJBPersistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.youwang.test.CRUD;



public class TestEJB extends CRUD{

    public static void main(String[] args) {
        SessionFactory sessionFactory = configSessionFactory("hibernate.cfg.xml");
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        Student student = new Student();
        student.setId(1);
        student.setUsername("admin");
        student.setPassword("admin");

        session.save(student);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

}
