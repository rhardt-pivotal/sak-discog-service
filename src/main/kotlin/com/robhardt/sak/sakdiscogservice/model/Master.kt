package com.robhardt.sak.sakdiscogservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection="masters")
data class Master (val title: String?, val artist: String?,
                   val year: Number?, val style: String?, val updated_on: String?,
                   var index: Number?){}