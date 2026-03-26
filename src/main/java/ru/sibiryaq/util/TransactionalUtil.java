package ru.sibiryaq.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionalUtil {

    public static <T> T execute(Function<Session, T> action) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            T result = action.apply(session);

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public static void executeVoid(Consumer<Session> action) {
        execute(session -> {
            action.accept(session);
            return null;
        });
    }

    public static <T> T executeRead(Function<Session, T> action) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return action.apply(session);
        }
    }
}
