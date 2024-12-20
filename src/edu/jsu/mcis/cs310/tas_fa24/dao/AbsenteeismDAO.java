package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Absenteeism;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
/**
*
* @author droge
*/
public class AbsenteeismDAO {
    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_INSERT = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE absenteeism SET employeeid = ?, percentage = ?";
    private final DAOFactory daoFactory;
 
    AbsenteeismDAO(DAOFactory daoFactory) {
 
        this.daoFactory = daoFactory;
 
    }
 
    public Absenteeism find(Employee employee, LocalDate payperiod) {
 
        Absenteeism absenteeism = null;
 
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        try {
 
            Connection conn = daoFactory.getConnection();
 
            if (conn.isValid(0)) {
 
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());
                ps.setDate(2, java.sql.Date.valueOf(payperiod));
                boolean hasresults = ps.execute();
 
                if (hasresults) {
 
                    rs = ps.getResultSet();
 
                    while (rs.next()) {
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        absenteeism = new Absenteeism(employee, payperiod, percentage);
 
                    }
 
                }
 
            }
 
        } catch (SQLException e) {
 
            throw new DAOException(e.getMessage());
 
        } finally {
 
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
 
        }
 
        return absenteeism;
 
    }
     public Absenteeism create (Absenteeism a1) {

 
        PreparedStatement ps = null;
        ResultSet rs = null;
 
            try {
 
            Connection conn = daoFactory.getConnection();
 
            if (conn.isValid(0)) {
            ps = conn.prepareStatement(QUERY_UPDATE,Statement.RETURN_GENERATED_KEYS);
            
                        //LocalDateTime payperiod_start= a1.getPayperiod().atStartOfDay();
                        //LocalDateTime payperiod_start= a1.getPayperiod().atStartOfDay();
                        //LocalDateTime payperiod_start1 = a1.getPayperiod().atStartOfDay();
                        //LocalDateTime payperiod_end= a1.getPayperiod().datesUntil(payperiod_start.toLocalDate());
                        //LocalTime payperiod_to_localTime = a1.getPayperiod().toLocalTime();
                        //long epochSeconds = payperiod_start.toEpochSecond(ZoneOffset.UTC);
                        //Duration stop = Duration.between(payperiod_start, payperiod_end);
                        ps.setInt(1, a1.getEmployee().getId());
                        //ps.setDate(2, java.sql.Date.valueOf(a1.getPayperiod()));
                        ps.setBigDecimal(2, a1.getPercentage());
                        ps.setDate(3, java.sql.Date.valueOf(a1.getPayperiod()));
                        ps.executeUpdate();

            int updateCount = ps.executeUpdate();
                if (updateCount > 0) {
                    rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    // updates the existing the records
                    // Inserts the new record
                    ps = conn.prepareStatement(QUERY_INSERT,Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, a1.getEmployee().getId());
                        ps.setDate(2, java.sql.Date.valueOf(a1.getPayperiod()));
                        ps.setBigDecimal(3, a1.getPercentage());
                        ps.executeUpdate();
                    }
                }
 
            }
 
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return a1;
        }
}