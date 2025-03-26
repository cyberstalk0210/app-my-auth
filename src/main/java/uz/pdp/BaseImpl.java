package uz.pdp;

import uz.pdp.product.Product;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BaseImpl<T, ID> implements BaseDAO<T, ID> {

    protected Class<T> entityClass;


    public BaseImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    Connection createConnection() {
        Properties properties = readProperties();

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public T save(T entity) {

        //object to relation

        try {
            Field[] fields = BaseImpl.this.entityClass.getDeclaredFields();
            StringBuilder sb = new StringBuilder("INSERT INTO ");
            sb.append(BaseImpl.this.entityClass.getSimpleName().toLowerCase());//insert into product(id, name, price) VALUES()
            sb.append("(");
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals("id"))
                    continue;
                sb.append(field.getName());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") VALUES(");

            for (Field field : fields) {
                if (field.getName().equals("id"))
                    continue;
                sb.append("?,");
            }

            sb.setCharAt(sb.length() - 1, ')');
            sb.append(" RETURNING *");

            Connection connection = createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

            int i = 1;
            for (Field field : fields) {
                if (field.getName().equals("id"))
                    continue;
                preparedStatement.setObject(i, field.get(entity));
                i++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            entityClass.getDeclaredField("id").setAccessible(true);
            Object id = resultSet.getObject("id");
            BaseImpl.this.entityClass.getDeclaredMethod("setId", Integer.class).invoke(entity, id);
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public T findById(ID id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return findAll(false, 0, 0);
    }

    @Override
    public List<T> findAll(int page, int size) {
        return findAll(true, page, size);
    }

    private List<T> findAll(boolean pageable, int page, int size) {
        try {
            Field[] fields = entityClass.getDeclaredFields();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(entityClass.getSimpleName());
            if (pageable) {
                sb.append(" LIMIT ");
                sb.append(size);
                sb.append(" OFFSET ");
                sb.append((page - 1) * size);
            }

            Connection connection = createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());

            List<T> list = new ArrayList<>();
            Class<?>[] array = new Class[fields.length];
            for (int i = 0; i < fields.length; i++) {
                array[i] = fields[i].getType();
            }


            //relation to object
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Object[] arguments = Arrays.stream(fields).map(field -> {
                    try {

                        Object object = resultSet.getObject(field.getName());
                        if (object instanceof BigDecimal b)
                            object = b.doubleValue();
                        return object;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray();

                T element = (T) entityClass.getConstructor(array).newInstance(arguments);
                list.add(element);
            }
            return list;
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream input = BaseImpl.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}
