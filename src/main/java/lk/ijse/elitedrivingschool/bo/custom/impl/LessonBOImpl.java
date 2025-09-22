package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.LessonBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.LessonDAO;
import lk.ijse.elitedrivingschool.dto.LessionDTO;
import lk.ijse.elitedrivingschool.entity.Lesson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonBOImpl implements LessonBO {

    private final LessonDAO lessonDAO = DAOFactory.getInstance().getDAO(DAOTypes.LESSON);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public ArrayList<LessionDTO> getAllLessons() throws SQLException, ClassNotFoundException {
        try {
            List<Lesson> lessons = lessonDAO.getAll();
            ArrayList<LessionDTO> lessonDTOs = new ArrayList<>();
            
            for (Lesson lesson : lessons) {
                lessonDTOs.add(converter.getLessonDTO(lesson));
            }
            return lessonDTOs;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve lessons", e);
        }
    }

    @Override
    public ArrayList<String> getAllLessonIds() throws SQLException, ClassNotFoundException {
        return (ArrayList<String>) lessonDAO.getAllIds();
    }

    @Override
    public boolean saveLessons(LessionDTO lessonDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Lesson> optionalLesson = lessonDAO.findById(lessonDTO.getLessionId());
            if (optionalLesson.isPresent()) {
                throw new DuplicateException("Duplicate lesson ID: " + lessonDTO.getLessionId());
            }
            
            Lesson lesson = converter.getLesson(lessonDTO);
            return lessonDAO.save(lesson);
            
        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save lesson", e);
        }
    }

    @Override
    public boolean updateLessons(LessionDTO lessonDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Lesson> optionalLesson = lessonDAO.findById(lessonDTO.getLessionId());
            if (optionalLesson.isEmpty()) {
                throw new NotFoundException("Lesson not found with ID: " + lessonDTO.getLessionId());
            }
            
            Lesson lesson = converter.getLesson(lessonDTO);
            return lessonDAO.update(lesson);
            
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update lesson", e);
        }
    }

    @Override
    public boolean deleteLessons(String id) throws SQLException, ClassNotFoundException {
        try {
            Optional<Lesson> optionalLesson = lessonDAO.findById(id);
            if (optionalLesson.isEmpty()) {
                throw new NotFoundException("Lesson not found with ID: " + id);
            }
            
            return lessonDAO.delete(id);
            
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete lesson", e);
        }
    }

    @Override
    public String generateNewLessonId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            String lastId = lessonDAO.getLastId();
            char tableChar = 'L';

            if (lastId != null && !lastId.isEmpty()) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format(tableChar + "%03d", nextIdNumber);
            }
            return tableChar + "001";
        } catch (Exception e) {
            throw new SQLException("Failed to generate next lesson ID", e);
        }
    }
}