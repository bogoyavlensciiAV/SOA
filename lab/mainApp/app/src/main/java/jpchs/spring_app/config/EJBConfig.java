package jpchs.spring_app.config;

import jpchs.StatisticsBean;
import jpchs.StudyGroupBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
public class EJBConfig {

    private final String EJB_MODULE = "ejb";

    @Bean
    public StudyGroupBean studyGroupBean() throws NamingException {
        return lookup("StudyGroupBeanImpl", StudyGroupBean.class);
    }

    @Bean
    public StatisticsBean statisticsBean() throws NamingException {
        return lookup("StatisticsBeanImpl", StatisticsBean.class);
    }

    private <T> T lookup(String beanName, Class<T> iface) throws NamingException {
        var jndi = "java:app/" + EJB_MODULE + "/" + beanName + "!" + iface.getName();
        var ctx = new InitialContext();
        try {
            return iface.cast(ctx.lookup(jndi));
        } finally {
            ctx.close();
        }
    }
}
