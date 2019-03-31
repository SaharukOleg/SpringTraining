package com.example;

import com.example.domain.Message;
import com.example.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/*
  Контроллер це програмний модуль який по цьому шляху @GetMapping("/greeting") слухає запити від користувача і повертає якісь дані
    */
@Controller
public class GreetingController {
    /*
    Анотація @GetMapping гарантує, що запити HTTP GET запитів до /greeting відображаються у методі greeting ().
     */
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        /*
        @RequestParam пов'язує значення імені параметра параметра запиту в параметр імені методу greeting ().
         */
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }


    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag); // створили повідомлення
        messageRepository.save(message); // зберегли повідомлення

        Iterable<Message> messages = messageRepository.findAll();  // взяли з репозиторію
        model.put("messages", messages);// положили в модель
        return "main"; //віддали пользователю
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);// положили в модель

        return "main";
    }

}
