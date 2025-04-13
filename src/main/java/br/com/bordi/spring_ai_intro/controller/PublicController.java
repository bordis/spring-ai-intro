package br.com.bordi.spring_ai_intro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bordi.spring_ai_intro.core.model.AnswerRecord;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalInfo;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalRequest;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalResponse;
import br.com.bordi.spring_ai_intro.core.model.GetQuestionsResponse;
import br.com.bordi.spring_ai_intro.core.model.QuestionRecord;
import br.com.bordi.spring_ai_intro.core.service.OllamaService;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @Autowired
    private OllamaService ollamaService;

    @GetMapping("")
    public ResponseEntity<String> alive() {
        return ResponseEntity.ok("Running!");
    }

    @GetMapping("/answer")
    public ResponseEntity<AnswerRecord> getAnswer() {
        AnswerRecord answer = ollamaService.getAnswer("Me conte uma piada sobre programação.");
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/ask")
    public ResponseEntity<AnswerRecord> ask(@RequestBody QuestionRecord questionModel) {
        AnswerRecord answer = ollamaService.getAnswer(questionModel.question());
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/capital")
    public ResponseEntity<AnswerRecord> getCapital(@RequestBody GetCapitalRequest request) {
        AnswerRecord answer = ollamaService.getCapital(request.stateOrCountry());
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/capitalWithInfo")
    public ResponseEntity<AnswerRecord> getCapitalWithInfo(@RequestBody GetCapitalRequest request) {
        AnswerRecord answer = ollamaService.getCapitalWithInfo(request.stateOrCountry());
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/capitalWithInfoConverter")
    public ResponseEntity<GetCapitalInfo> capitalWithInfoConverter(@RequestBody GetCapitalRequest request) {
        GetCapitalInfo answer = ollamaService.getCapitalWithInfoConverter(request.stateOrCountry());
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/capitalJson")
    public ResponseEntity<String> getCapitalJson(@RequestBody GetCapitalRequest request) {
        String answer = ollamaService.getAnswerString(request.stateOrCountry());
        if (answer == "") {
            return ResponseEntity.badRequest().body("Invalid JSON response");
        } else {
            return ResponseEntity.ok(answer);
        }
    }

    @PostMapping("/capitalConverter")
    public ResponseEntity<GetCapitalResponse> getCapitalConverter(@RequestBody GetCapitalRequest request) {
        GetCapitalResponse answer = ollamaService.getCapitalResponseConverter(request);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<GetQuestionsResponse>> getQuestions() {
        List<GetQuestionsResponse> questions = ollamaService.getQuestionResponseConverter();
        return ResponseEntity.ok(questions);
    }
}
