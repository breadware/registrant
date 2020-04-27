package br.com.breadware.configuration;

import br.com.breadware.properties.PropertiesPath;
import br.com.breadware.properties.thymeleaf.ThymeleafProperties;
import br.com.breadware.properties.thymeleaf.ThymeleafTemplateResolverProperties;
import br.com.breadware.util.PathUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.spring5.messageresolver.SpringMessageResolver;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.inject.Named;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ThymeleafConfiguration {

    public static final String HTML_THYMELEAF_TEMPLATE_RESOLVER_PROPERTIES_NAME = "html";

    public static final String TEXT_THYMELEAF_TEMPLATE_RESOLVER_PROPERTIES_NAME = "text";

    @Bean
    @ConfigurationProperties(PropertiesPath.THYMELEAF_TEMPLATE_RESOLVERS)
    @Validated
    public Map<String, ThymeleafTemplateResolverProperties> createThymeleafTemplateResolverPropertiesMap() {
        return new HashMap<>();
    }

    @Bean(BeanNames.THYMELEAF_HTML_TEMPLATE_RESOLVER)
    public ITemplateResolver createThymeleafHtmlTemplateResolver(ThymeleafProperties thymeleafProperties, Map<String, ThymeleafTemplateResolverProperties> thymeleafTemplateResolverPropertiesMap, PathUtil pathUtil) {
        ThymeleafTemplateResolverProperties htmlThymeleafTemplateResolverProperties = thymeleafTemplateResolverPropertiesMap.get(HTML_THYMELEAF_TEMPLATE_RESOLVER_PROPERTIES_NAME);
        return createTemplateResolver(thymeleafProperties, htmlThymeleafTemplateResolverProperties, pathUtil);
    }

    @Bean(BeanNames.THYMELEAF_TEXT_TEMPLATE_RESOLVER)
    public ITemplateResolver createThymeleafTextTemplateResolver(ThymeleafProperties thymeleafProperties, Map<String, ThymeleafTemplateResolverProperties> thymeleafTemplateResolverPropertiesMap, PathUtil pathUtil) {
        ThymeleafTemplateResolverProperties htmlThymeleafTemplateResolverProperties = thymeleafTemplateResolverPropertiesMap.get(TEXT_THYMELEAF_TEMPLATE_RESOLVER_PROPERTIES_NAME);
        return createTemplateResolver(thymeleafProperties, htmlThymeleafTemplateResolverProperties, pathUtil);
    }

    private SpringResourceTemplateResolver createTemplateResolver(ThymeleafProperties thymeleafProperties, ThymeleafTemplateResolverProperties thymeleafTemplateResolverProperties, PathUtil pathUtil) {
        String prefix = createTemplateResolverPrefix(thymeleafProperties, thymeleafTemplateResolverProperties, pathUtil);

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(prefix);
        templateResolver.setTemplateMode(thymeleafTemplateResolverProperties.getMode());
        templateResolver.setResolvablePatterns(thymeleafTemplateResolverProperties.getResolvablePatterns());
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return templateResolver;
    }

    private String createTemplateResolverPrefix(ThymeleafProperties thymeleafProperties, ThymeleafTemplateResolverProperties thymeleafTemplateResolverProperties, PathUtil pathUtil) {
        StringBuilder prefixStringBuilder = new StringBuilder();
        Optional.ofNullable(thymeleafProperties.getTemplatePrefix())
                .ifPresent(prefixStringBuilder::append);
        prefixStringBuilder.append(thymeleafTemplateResolverProperties.getPrefix());
        return pathUtil.removeMultipleSeparators(prefixStringBuilder.toString());
    }

    @Bean(BeanNames.MESSAGE_RESOLVER)
    public IMessageResolver createMessageResolver(MessageSource messageSource) {
        SpringMessageResolver springMessageResolver = new SpringMessageResolver();
        springMessageResolver.setMessageSource(messageSource);
        return springMessageResolver;
    }

    @Bean(BeanNames.TEMPLATE_ENGINE)
    public TemplateEngine createTemplateEngine(@Named(BeanNames.THYMELEAF_HTML_TEMPLATE_RESOLVER) ITemplateResolver htmlTemplateResolver, @Named(BeanNames.THYMELEAF_TEXT_TEMPLATE_RESOLVER) ITemplateResolver textTemplateResolver, IMessageResolver messageResolver) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver);
        templateEngine.addTemplateResolver(textTemplateResolver);
        templateEngine.setMessageResolver(messageResolver);
        return templateEngine;
    }
}
