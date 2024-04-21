package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.User

interface UseCase<Request, Response> {
    val name: String
    suspend fun execute(user: User, request: Request): Response
}