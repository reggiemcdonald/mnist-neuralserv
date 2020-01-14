package org.reggiemcdonald.persistence.entity.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.stream.Stream;

public class TwoDimDoubleArrayUserType implements UserType {

    protected static final int[] SQL_TYPES = { Types.ARRAY };

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.ARRAY };
    }

    @Override
    public Class<double[][]> returnedClass() {
        return double[][].class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null)
            return o1 == null;
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        if (resultSet.wasNull())
            return null;
        if (resultSet.getArray(strings[0]) == null)
            return new double[0][];
        Array array = resultSet.getArray(strings[0]);
        Double[][] twoDeeDoubleArray = (Double[][]) array.getArray();
        double[][] twoDeeArray = new double[twoDeeDoubleArray.length][twoDeeDoubleArray[0].length];
        for (int i = 0 ; i < twoDeeArray.length ; i++)
            for (int j = 0 ; j < twoDeeArray[i].length; j++)
                twoDeeArray[i][j] = (double) twoDeeDoubleArray[i][j];

        return twoDeeArray;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        Connection conn = preparedStatement.getConnection();
        if (o == null) {
            preparedStatement.setNull(i, SQL_TYPES[0]);
        } else {
            double[][] oAsArray = (double[][]) o;
            Array array = conn.createArrayOf("float8", oAsArray);
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (double[][]) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
