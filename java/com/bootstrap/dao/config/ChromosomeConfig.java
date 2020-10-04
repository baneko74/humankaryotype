package com.bootstrap.dao.config;

import java.util.Locale;

import javax.validation.Validator;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScan(basePackages = "com.bootstrap")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bootstrap.dao.repositories.jpa")
@EnableSolrRepositories(basePackages = { "com.bootstrap.dao.repositories.solr" })
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ChromosomeConfig implements WebMvcConfigurer {

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry reg) {
		reg.addInterceptor(localeChangeInterceptor());
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() throws Exception {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		resourceBundleMessageSource.setBasename("i18n/messages");
		resourceBundleMessageSource.setFallbackToSystemLocale(false);
		return resourceBundleMessageSource;
	}

	@Bean
	public CookieLocaleResolver cookieResolver() {
		CookieLocaleResolver cookieResolver = new CookieLocaleResolver();
		cookieResolver.setDefaultLocale(Locale.ENGLISH);
		cookieResolver.setCookieMaxAge(3600);
		return cookieResolver;
	}

	@Bean
	public Validator validator() throws Exception {
		LocalValidatorFactoryBean validatorBean = new LocalValidatorFactoryBean();
		validatorBean.setValidationMessageSource(messageSource());
		return validatorBean;
	}

	@Bean
	public FilterRegistrationBean<ChromosomeFilter> chromosomeFilter() {
		FilterRegistrationBean<ChromosomeFilter> regBean = new FilterRegistrationBean<ChromosomeFilter>();
		regBean.setFilter(new ChromosomeFilter());
		regBean.addUrlPatterns(new String[] {});
		return regBean;

	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}

}
