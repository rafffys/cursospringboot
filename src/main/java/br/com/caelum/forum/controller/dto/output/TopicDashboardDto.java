package br.com.caelum.forum.controller.dto.output;

import br.com.caelum.forum.model.Category;

import java.util.List;

public class TopicDashboardDto {
    private String categoryName;
    private List<String> subcategories;
    private int allTopics;
    private int lastWeekTopics;
    private int unansweredTopics;

    public TopicDashboardDto(String categoryName,
                             List<String> subcategories,
                             int allTopics,
                             int lastWeekTopics,
                             int unansweredTopics) {
        this.categoryName = categoryName;
        this.subcategories = subcategories;
        this.allTopics = allTopics;
        this.lastWeekTopics = lastWeekTopics;
        this.unansweredTopics = unansweredTopics;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public int getAllTopics() {
        return allTopics;
    }

    public void setAllTopics(int allTopics) {
        this.allTopics = allTopics;
    }

    public int getLastWeekTopics() {
        return lastWeekTopics;
    }

    public void setLastWeekTopics(int lastWeekTopics) {
        this.lastWeekTopics = lastWeekTopics;
    }

    public int getUnansweredTopics() {
        return unansweredTopics;
    }

    public void setUnansweredTopics(int unansweredTopics) {
        this.unansweredTopics = unansweredTopics;
    }

    public static TopicDashboardDto montaDTO(Category category, int allTopics, int lastWeekTopics, int unansweredTopics) {
        List<String> subCategorias = category.getSubcategoryNames();
        return new TopicDashboardDto(category.getName(), subCategorias, allTopics, lastWeekTopics, unansweredTopics);
    }
}
