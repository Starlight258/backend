package com.wooteco.wiki.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.random.Random

@Configuration
class RandomConfig {

    @Bean
    fun random(): Random = Random.Default
}
