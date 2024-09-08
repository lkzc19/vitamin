package org.example.model

import org.example.anno.NoArg
import java.time.Instant

@NoArg
open class BaseModel(

    open val id: Long?,

    open val createdAt: Instant?,

    open val updatedAt: Instant?,

    open val deletedAt: Instant?,
)