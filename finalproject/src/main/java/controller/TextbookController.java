package controller;


import lombok.RequiredArgsConstructor;
import model.Textbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.TextbookRepository;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TextbookController {

    private final TextbookRepository textbookRepository;

    @GetMapping("/new")
    public String showTextbookForm(model.Textbook textbook) {
        return "add";
    }

    @PostMapping("/add")
    public String getTextbooks(@Valid Textbook textbook, BindingResult result){
        if (result.hasErrors()){
            return "add";
        }
        textbookRepository.save(textbook);
        return "redirect:textbook";
    }

    @GetMapping("/textbook")
    public String getTextbooks(Model model){
        List<Textbook> allTextbooks = textbookRepository.findAll();
        model.addAttribute("textbooks", allTextbooks.isEmpty() ? null : allTextbooks);
        return "textbooks";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Textbook textbook = textbookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid textbook Id:" + id));
        model.addAttribute("textbook", textbook);
        return "update";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Textbook textbook, BindingResult result) {
        if (result.hasErrors()) {
            textbook.setId(id);
            return "update";
        }
        textbookRepository.save(textbook);
        return "redirect:/textbook";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Textbook textbook = textbookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid textbook Id:" + id));
        textbookRepository.delete(textbook);
        return "redirect:/textbook";
    }
}