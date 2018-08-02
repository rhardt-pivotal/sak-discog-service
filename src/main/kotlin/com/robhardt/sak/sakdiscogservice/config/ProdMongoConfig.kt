package com.robhardt.sak.sakdiscogservice.config


import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration



@Configuration
@ConfigurationProperties("vcap.services.mongo.credentials")
class ProdMongoConfig(@Value("\${vcap.services.mongo.credentials.uri}") val mongoUri: String) : AbstractReactiveMongoConfiguration() {


    //mongodb://admin:Dude1234!@ec2-52-37-229-9.us-west-2.compute.amazonaws.com/discogs?authSource=admin

    override fun reactiveMongoClient(): MongoClient {
        System.out.println("XXX********************")
        System.out.println(mongoUri)
        System.out.println("XXX********************")
        return MongoClients.create(mongoUri)
    }

    override fun getDatabaseName(): String {
        return "discogs"
    }
}