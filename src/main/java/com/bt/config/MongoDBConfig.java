package com.bt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

@Configuration
public class MongoDBConfig {

	@Autowired
    private Environment env;

//    @Bean
//    public MongoDbFactory mongoDbFactory() {
//        return new SimpleMongoDbFactory(new MongoClientURI(env.getProperty("spring.data.mongodb.uri")));
//    }
    // mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[database][?options]]
	// mongodb://mongodb0.example.com:27017/admin
    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoClientDbFactory("mongodb://" + 
        		env.getProperty("spring.data.mongodb.host") +
        		":" + 
        		env.getProperty("spring.data.mongodb.port") +
        		"/test");
    }

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

    }
}
