package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.UserBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.UserDAO;
import lk.ijse.elitedrivingschool.dto.UserDTO;
import lk.ijse.elitedrivingschool.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        try {
            List<User> users = userDAO.getAll();
            ArrayList<UserDTO> userDTOS = new ArrayList<>();
            for (User user : users) {
                userDTOS.add(converter.getUserDTO(user));
            }
            return userDTOS;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve users", e);
        }
    }

    @Override
    public boolean saveUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<User> optionalUser = userDAO.findById(userDTO.getUserId());
            if (optionalUser.isPresent()) {
                throw new DuplicateException("Duplicate user ID: " + userDTO.getUserId());
            }

            // Hash the password before saving
            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
            userDTO.setPassword(hashedPassword);

            User user = converter.getUser(userDTO);
            return userDAO.save(user);
        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save user", e);
        }
    }

    @Override
    public boolean updateUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<User> optionalUser = userDAO.findById(userDTO.getUserId());
            if (optionalUser.isEmpty()) {
                throw new NotFoundException("User not found with ID: " + userDTO.getUserId());
            }

            // Keep existing password if empty
            if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                userDTO.setPassword(optionalUser.get().getPassword());
            } else {
                // Hash new password
                userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
            }

            User user = converter.getUser(userDTO);
            return userDAO.update(user);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update user", e);
        }
    }

    @Override
    public boolean deleteUsers(String id) throws SQLException, ClassNotFoundException {
        try {
            Optional<User> optionalUser = userDAO.findById(id);
            if (optionalUser.isEmpty()) {
                throw new NotFoundException("User not found with ID: " + id);
            }
            return userDAO.delete(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete user", e);
        }
    }

    @Override
    public String generateNewUserId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            String lastId = userDAO.getLastId();
            char tableChar = 'U';

            if (lastId != null && !lastId.isEmpty()) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format(tableChar + "%03d", nextIdNumber);
            }
            return tableChar + "001";
        } catch (Exception e) {
            throw new SQLException("Failed to generate next User ID", e);
        }
    }

    @Override
    public UserDTO login(String username, String password) throws SQLException, ClassNotFoundException {
        try {
            Optional<User> optionalUser = userDAO.findById(username);
            if (optionalUser.isEmpty()) return null;

            User user = optionalUser.get();
            // Check entered password against hashed password in DB
            if (BCrypt.checkpw(password, user.getPassword())) {
                return new UserDTO(user.getUserId(), user.getUserName(), null, user.getRole());
            }
            return null;
        } catch (Exception e) {
            throw new SQLException("Login failed", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String usernameInput) throws SQLException {
        try {
            List<User> allUsers = userDAO.getAll();
            for (User user : allUsers) {
                if (user.getUserName().equalsIgnoreCase(usernameInput)) {
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new SQLException("Failed to find user by username", e);
        }
    }
}
