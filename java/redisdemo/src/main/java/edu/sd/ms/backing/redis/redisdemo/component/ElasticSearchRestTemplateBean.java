package edu.sd.ms.backing.redis.redisdemo.component;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

//@Component
public class ElasticSearchRestTemplateBean {
	private final ElasticsearchRestTemplate template;
	
	public ElasticSearchRestTemplateBean(ElasticsearchRestTemplate template) {
		this.template = template;
	}
}
