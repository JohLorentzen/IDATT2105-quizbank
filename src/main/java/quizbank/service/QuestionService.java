package quizbank.service;

import quizbank.dto.QuestionDTO;
import quizbank.model.Question;
import quizbank.model.Quiz;

import java.util.List;
import java.util.Set;

/**
 * Interface for the QuestionService.
 * This service provides methods for converting between Question and QuestionDTO objects,
 * and for fetching all tags from all questions.
 */
public interface QuestionService {

    /**
     * Converts a list of Question entities to a list of QuestionDTOs.
     *
     * @param questions the list of Question entities
     * @return the list of converted QuestionDTOs
     */
    List<QuestionDTO> toDto(List<Question> questions);

    /**
     * Converts a list of QuestionDTOs to a list of Question entities.
     *
     * @param questionDTOs the list of QuestionDTOs
     * @param quiz the Quiz entity
     * @return the list of converted Question entities
     */
    List<Question> toEntity(List<QuestionDTO> questionDTOs, Quiz quiz);

    /**
     * Converts a Question entity to a QuestionDTO.
     *
     * @param question the Question entity
     * @return the converted QuestionDTO
     */
    QuestionDTO toDto(Question question);

    /**
     * Fetches all tags from all questions.
     *
     * @return a set of all tags
     */
    Set<String> getAllTags();
}