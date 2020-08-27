package com.tenten.yugibrick.domain.service

import java.util.UUID

/**
 *
 * Created by Exequiel Egbert V. Ponce on 5/11/2020.
 */
class IDService {
    fun generateID() = UUID.randomUUID().toString()
}
