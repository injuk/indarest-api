package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.User

interface UseCase <Request, Response> {
    val name: String
    fun execute(user: User, data: Request): Response
}