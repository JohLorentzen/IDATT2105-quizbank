package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.dto.QuestionDTO;
import quizbank.model.Question;
import quizbank.model.Quiz;
import quizbank.repository.QuestionRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing questions.
 * This class provides methods for converting between Question and QuestionDTO objects,
 * and for fetching all tags from all questions.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Converts a list of Question entities to a list of QuestionDTOs.
     *
     * @param questions the list of Question entities
     * @return the list of converted QuestionDTOs
     */
    @Override
    public List<QuestionDTO> toDto(List<Question> questions) {
        return questions.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Converts a list of QuestionDTOs to a list of Question entities.
     *
     * @param questionDTOs the list of QuestionDTOs
     * @param quiz the Quiz entity
     * @return the list of converted Question entities
     */
    public List<Question> toEntity(List<QuestionDTO> questionDTOs, Quiz quiz) {
        return questionDTOs.stream().map(dto -> {
            Question question = new Question();
            question.setId(dto.getQuestionId());
            question.setProblem(dto.getProblem());
            question.setSolution(dto.getSolution());
            question.setQuiz(quiz);
            question.setType(dto.getType());
            question.setChoices(dto.getChoices());
            question.setTags(dto.getTags());
            question.setImage(dto.getImage());
            return question;
        }).collect(Collectors.toList());
    }

    /**
     * Converts a Question entity to a QuestionDTO.
     *
     * @param question the Question entity
     * @return the converted QuestionDTO
     */
    @Override
    public QuestionDTO toDto(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(question.getId());
        questionDTO.setProblem(question.getProblem());
        questionDTO.setSolution(question.getSolution());
        questionDTO.setType(question.getType());
        questionDTO.setChoices((question.getChoices()));
        questionDTO.setTags(question.getTags());
        questionDTO.setImage(question.getImage());
        return questionDTO;
    }

    /**
     * Fetches all tags from all questions.
     *
     * @return a set of all tags
     */
    @Override
    public Set<String> getAllTags() {
        return questionRepository.findAll().stream().map(Question::getTags).flatMap(Set::stream).collect(Collectors.toSet());
    }
}