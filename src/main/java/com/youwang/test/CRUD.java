package com.youwang.test;
import com.youwang.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.util.List;
import java.lang.StringBuilder;

public class CRUD {

    public static void main(String[] args) {

        // 建立 sessionFactory
        SessionFactory sessionFactory = configSessionFactory("hibernate.cfg.xml");
        Session session = sessionFactory.openSession();
        // Create
        Create(session, 1, "admin", "adm123");
        // Read
        printTable(Read(session,""));
        // Update
        Update(session, "admin", "updateTwice");
        printTable(Read(session,""));
        // Delete
        Delete(session, "admin");
        printTable(Read(session,""));

        session.close();
        sessionFactory.close();

    }

    public static SessionFactory configSessionFactory(String configFile) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                //如果不写配置文件名就会使用默认配置文件名hibernate.cfg.xml
                .configure(configFile)
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void Create(Session session, int id, String name, String password) {
        session.beginTransaction();
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(password);
        session.save(user);
        session.getTransaction().commit();
    }

    public static List<User> Read(Session session, String name) {
        session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        Query query;
        List<User> res;
        if(name.equals("")) {
            sb.append("from ").append( User.class.getName() );
            query = session.createQuery(sb.toString());
        } else {
            sb.append("from ").append(User.class.getName()).append(" where user_username=:name");
            query = session.createQuery(sb.toString());
            query.setParameter("name", name );
        }
        res = query.list();
        session.getTransaction().commit();
        return res;
    }

    public static void Update(Session session, String name, String newPassword) {
        List<User> users = Read(session, name);
        session.beginTransaction();
        for(User user : users) {
            user.setPassword(newPassword);
            session.update(user);
        }
        session.getTransaction().commit();
    }

    public static void Delete(Session session, String name) {
        List<User> users = Read(session, name);
        session.beginTransaction();
        for(User user : users) {
            session.delete(user);
        }
        session.getTransaction().commit();
    }

    public static void printTable(List<User> users) {
        for(User user : users ) {
            System.out.println("id = " + user.getId() + ", name = " + user.getUsername() + ", password = " + user.getPassword());
        }
    }


}
