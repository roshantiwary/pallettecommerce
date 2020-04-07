package com.pallette.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories(basePackages = { "com.pallette.solr.repositories"})
public class SolrContext {

	@Value("${pallette.solr.baseUrl}")
	private String baseURL;

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder(baseURL).build();
	}

	@Bean
	public SolrTemplate productsTemplate() {
		return new SolrTemplate(solrClient());
	}

}

/*
@Configuration
@EnableSolrRepositories(basePackages = { "com.pallette.solr.repositories",
"com.pallette.solr.repository" })
public class SolrContext {

    static final String SOLR_HOST = "SOLR_HOST";

    @Autowired
    private Environment environment;

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder(environment.getProperty(SOLR_HOST)).build();
    }

    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(solrClient());
    }

    @Bean
    public SolrTemplate core1Template() {
        return new SolrTemplate(new HttpSolrClient.Builder(environment.getProperty(SOLR_HOST)+"/core1").build());
    }

    @Bean
    public SolrTemplate core2Template() {
        return new SolrTemplate(new HttpSolrClient.Builder(environment.getProperty(SOLR_HOST)+"/core2").build());
    }
}
*/