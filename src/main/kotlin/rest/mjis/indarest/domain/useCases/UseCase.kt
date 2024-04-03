package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.User

interface UseCase <in Req, out Res> {
    fun execute(user: User, data: Req): Res
}