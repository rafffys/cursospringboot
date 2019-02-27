package br.com.caelum.forum.controller;

import br.com.caelum.forum.model.OpenTopicsByCategory;
import br.com.caelum.forum.repository.OpenTopicsByCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class ReportsController {
    @Autowired
    private OpenTopicsByCategoryRepository openTopicsByCategoryRepository;

    @GetMapping("/open-topics-by-category")
    public String showOpenTopicsByCategoryReport(Model model) {
        List<OpenTopicsByCategory> openTopicsByCategories = openTopicsByCategoryRepository.findAllByCurrentMonth();
        model.addAttribute("openTopics", openTopicsByCategories);
        return "report";
    }
}
