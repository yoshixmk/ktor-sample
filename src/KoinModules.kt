package yoshixmk

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import yoshixmk.databases.repository.MemoRepository
import yoshixmk.databases.repository.UserRepository
import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.irepository.IUserRepository
import yoshixmk.interfaces.controllers.IMemoController
import yoshixmk.interfaces.controllers.IUserController
import yoshixmk.interfaces.controllers.MemoController
import yoshixmk.interfaces.controllers.UserController
import yoshixmk.usecases.service.IMemoService
import yoshixmk.usecases.service.IUserService
import yoshixmk.usecases.service.MemoService
import yoshixmk.usecases.service.UserService

val koinModules = module(createdAtStart = true) {
    singleBy<IUserController, UserController>()
    singleBy<IUserService, UserService>()
    singleBy<IUserRepository, UserRepository>()

    singleBy<IMemoController, MemoController>()
    singleBy<IMemoService, MemoService>()
    singleBy<IMemoRepository, MemoRepository>()
}
