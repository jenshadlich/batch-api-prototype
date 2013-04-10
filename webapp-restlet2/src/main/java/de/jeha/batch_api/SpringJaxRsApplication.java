package de.jeha.batch_api;

import org.restlet.Component;
import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jns
 */
@org.springframework.stereotype.Component
public final class SpringJaxRsApplication extends JaxRsApplication implements InitializingBean, BeanFactoryAware {

    @Autowired
    @Qualifier("restletComponent")
    private Component component;

    public void afterPropertiesSet() throws Exception {
        setContext(component.getContext().createChildContext());

        final Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(BatchResource.class);

        add(new Application() {
            public Set<Class<?>> getClasses() {
                return classes;
            }
        });

        component.getDefaultHost().attach(this);
    }

    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        setObjectFactory(new ObjectFactory() {
            public <T> T getInstance(Class<T> jaxRsClass) throws InstantiateException {
                return beanFactory.getBean(jaxRsClass);
            }
        });
    }
}
