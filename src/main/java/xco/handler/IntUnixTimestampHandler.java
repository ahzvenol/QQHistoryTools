package xco.handler;

import org.apache.ibatis.type.*;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
@MappedTypes({ Date.class })
@MappedJdbcTypes({ JdbcType.INTEGER })
public class IntUnixTimestampHandler implements TypeHandler<Date> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter,
                             JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException(
                        "JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                ps.setNull(i, jdbcType.TYPE_CODE);
            } catch (SQLException e) {
                throw new TypeException(
                        "Error setting null for parameter #"
                                + i
                                + " with JdbcType "
                                + jdbcType
                                + " . "
                                + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
                                + "Cause: " + e, e);
            }
        } else {
            int time = (int) (parameter.getTime() / 1000);
            ps.setInt(i, time);
        }
    }

    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        int res = rs.getInt(columnName);
        long time = res * 1000L;
        return new Date(time);
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
        int res = rs.getInt(columnIndex);
        long time = res * 1000L;
        return new Date(time);
    }

    @Override
    public Date getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        int res = cs.getInt(columnIndex);
        long time = res * 1000L;
        return new Date(time);
    }
    
}
