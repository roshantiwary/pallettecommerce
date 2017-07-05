package com.pallette.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = { "com.pallette.solr.repositories",
		"com.pallette.solr.repository" }, multicoreSupport = true)
public class SolrContext {

	// @Value("${pallette.solr.baseUrl}")
	// private String baseURL;
	//
	// @Bean
	// public SolrClient solrClient() {
	// return new HttpSolrClient(baseURL);
	// }
	//
	// @Bean
	// public SolrTemplate productsTemplate(SolrClient solr) {
	// return new SolrTemplate(solr);
	// }

	@Bean
	public SolrOperations solrTemplate(SolrClient solr) {
		SolrTemplate template = new SolrTemplate(solr);
		return template;
	}
}
