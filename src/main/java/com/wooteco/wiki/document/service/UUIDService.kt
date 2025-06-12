package com.wooteco.wiki.document.service;

import org.springframework.stereotype.Service
import java.util.*

@Service
public class UUIDService {

    fun generate(): UUID {
        return UUID.randomUUID()
    }
}
