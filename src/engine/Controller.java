package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class Controller {
    @Autowired
    QuizService quizService;

    @GetMapping("/quiz")
    @ResponseBody
    public Quiz getQuiz() {
        return new Quiz(1, "The Java Logo", "What is depicted on the Java logo?", new String[] {"Robot", "Tea leaf", "Cup of coffee", "Bug"}, List.of(2));
    }

    @PostMapping("/quiz")
    public Response postQuiz(@RequestParam("answer") int answer) {
        if (answer == 2) {
            return new Response(true, "Congratulations, you're right!");
        } else {
            return new Response(false, "Wrong answer! Please, try again.");
        }
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz postQuizzes(@Valid @RequestBody IncompleteQuiz incompleteQuiz) {
        return quizService.createQuiz(incompleteQuiz);
    }

    @GetMapping("/quizzes")
    @ResponseBody
    public Collection<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/quizzes/{id}")
    @ResponseBody
    public Quiz getQuizzesId(@PathVariable("id") int id) {
        return quizService.getQuizById(id);
    }

    @PostMapping("/quizzes/{id}/solve")
    public Response postQuizzesIdSolve(
            @PathVariable("id") @Min(1) int id, @Valid @RequestBody Answer answer) {
        return quizService.solveQuizById(id, answer);
    }
}
