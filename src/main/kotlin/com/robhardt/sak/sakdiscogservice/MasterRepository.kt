package com.robhardt.sak.sakdiscogservice

import com.robhardt.sak.sakdiscogservice.model.Master
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterRepository : ReactiveMongoRepository<Master, String>