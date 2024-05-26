package com.rpn.adminmosque.repository.di

import com.rpn.smartgardening.repository.AuthAppRepository
import com.rpn.smartgardening.repository.MainRepository
import org.koin.dsl.module


val repositoryModule = module {
    // Repositories
    single { MainRepository() }
    single { AuthAppRepository(get(), get()) }
}
