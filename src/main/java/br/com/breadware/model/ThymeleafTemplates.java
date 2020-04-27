package br.com.breadware.model;

import java.util.Set;

public enum ThymeleafTemplates {

    NEW_ASSOCIATE_EMAIL("new-associate-email"),
    EXISTING_ASSOCIATE_EMAIL("existing-associate-email");

    private static final String HTML_SUFFIX = ".html";

    private static final String TEXT_SUFFIX = ".txt";

    private final String htmlTemplateName;

    private final String textTemplateName;

    private final Set<String> htmlTemplateFragment;

    ThymeleafTemplates(String templateName) {
        this.htmlTemplateName = templateName + HTML_SUFFIX;
        this.textTemplateName = templateName + TEXT_SUFFIX;
        this.htmlTemplateFragment = Set.of(templateName);
    }

    public String getHtmlTemplateName() {
        return htmlTemplateName;
    }

    public String getTextTemplateName() {
        return textTemplateName;
    }

    public Set<String> getHtmlTemplateFragment() {
        return htmlTemplateFragment;
    }
}
