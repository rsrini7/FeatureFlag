package com.srini.feature.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.srini.feature.model.MyUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MyUserDetails> getUser(String username) {
        try {
            final String sql = "select u.username username, u.password password, ur.role role from users u, user_roles ur where u.username = ? and u.username = ur.username";
            List<MyUserDetails> userDetails = jdbcTemplate.query(sql, new UserRowMapper(), username);
            return userDetails;
        } catch (EmptyResultDataAccessException ex) {
            return null;// should have proper handling of Exception
        }
    }

    private static class UserRowMapper implements RowMapper<MyUserDetails> {
        @Override
        public MyUserDetails mapRow(ResultSet rs, int row) throws SQLException {
            MyUserDetails userDetails = new MyUserDetails();
            userDetails.setUsername(rs.getString("username"));
            userDetails.setPassword(rs.getString("password"));
            userDetails.setRole(rs.getString("role"));
            return userDetails;
        }
    }
}