package br.com.bordi.spring_ai_intro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bordi.spring_ai_intro.core.model.AnswerRecord;
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
        AnswerRecord answer = ollamaService.getAnswer("tell me a dad joke.");
        return ResponseEntity.ok(answer);
    }

    @PostMapping("/ask")
    public ResponseEntity<AnswerRecord> ask(@RequestBody QuestionRecord questionModel) {
        AnswerRecord answer = ollamaService.getAnswer(questionModel.question());
        return ResponseEntity.ok(answer);
    }

}
