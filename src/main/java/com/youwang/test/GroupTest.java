package com.youwang.test;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.youwang.entity.User;
import com.youwang.entity.Group;

public class GroupTest extends CRUD{
    public static void main(String[] args) {
        SessionFactory sessionFactory = configSessionFactory("hibernate.cfg.xml");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // 新建一个 LOL 群组
        Group group = new Group();
        group.setGroupname("LOL_Group");

        // 新建两个用户
        Set<User> users = new HashSet<User>();
        User user1 = new User();
        user1.setUsername("Levis");
        user1.setPassword("111");
        User user2 = new User();
        user2.setUsername("Lee");
        user2.setPassword("222");

        users.add(user1);
        users.add(user2);

        // LOL 群组包含对应这两个用户
        group.setUsers(users);

        // 保存相关对象
        for (User user : users) {
            session.save(user);
        }
        session.save(group);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
