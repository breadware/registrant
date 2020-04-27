package br.com.breadware.configuration;

import br.com.breadware.exception.ApplicationContextRuntimeException;
import br.com.breadware.util.MessageRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrantApplicationContextAware implements ApplicationContextAware {

    public static final String CONTEXT_NOT_AVAILABLE_MESSAGE = "System context is not available yet.";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrantApplicationContextAware.class);
    private static ApplicationContext applicationContext;

    public static <T> T retrieveBean(Class<T> beanClass) {
        return retrieveBean(beanClass, null);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T retrieveBean(Class<T> beanClass, String beanName) {
        throwExceptionIfApplicationContextIsNotReady();

        Object bean;
        if (beanName != null) {
            bean = retrieveBeanFromApplicationContext(beanName);
        } else {
            bean = retrieveBeanFromApplicationContext(beanClass);
        }

        checkBeanType(bean, beanClass);

        return (T) bean;
    }

    private static void throwExceptionIfApplicationContextIsNotReady() {
        if (applicationContext == null) {
            throw new ApplicationContextRuntimeException(CONTEXT_NOT_AVAILABLE_MESSAGE);
        }
    }

    private static Object retrieveBeanFromApplicationContext(String beanName) {
        try {
            return applicationContext.getBean(beanName);

        } catch (BeansException exception) {
            throw new ApplicationContextRuntimeException(
                    "Could not locate bean \"" + beanName + "\".", exception);
        }
    }

    private static Object retrieveBeanFromApplicationContext(Class<?> beanClass) {
        try {
            return applicationContext.getBean(beanClass);

        } catch (BeansException exception) {
            throw new ApplicationContextRuntimeException(
                    "Could not locate a bean of type \"" + beanClass.getName() + "\".", exception);
        }
    }

    private static void checkBeanType(Object bean, Class<?> clazz) {
        if (!clazz.isInstance(bean)) {
            throw new ApplicationContextRuntimeException(
                    "Bean \"" + bean + "\" is not of type \"" + clazz.getCanonicalName() + "\".");
        }

    }

    public static Optional<MessageRetriever> retrieveMessageRetriever() {
        Optional<MessageRetriever> optionalMessageRetriever;

        if (applicationContext == null) {
            LOGGER.warn(CONTEXT_NOT_AVAILABLE_MESSAGE);
            optionalMessageRetriever = Optional.empty();
        } else {
            optionalMessageRetriever = Optional.of(retrieveBean(MessageRetriever.class));
        }

        return optionalMessageRetriever;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        synchronized (RegistrantApplicationContextAware.class) {
            RegistrantApplicationContextAware.applicationContext = applicationContext;
        }
    }
}