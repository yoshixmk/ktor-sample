package yoshixmk

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import yoshixmk.databases.repository.MockMemoRepository
import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.interfaces.controllers.IMemoController
import yoshixmk.interfaces.controllers.MemoController
import yoshixmk.usecases.service.IMemoService
import yoshixmk.usecases.service.MemoService
import yoshixmk.usecases.service.MockMemoService

val testKoinModules = module(createdAtStart = true) {
    singleBy<IMemoController, MemoController>() // テスト対象
    singleBy<IMemoService, MockMemoService>()
}

val testServiceModules = module(createdAtStart = true) {
    singleBy<IMemoService, MemoService>() // テスト対象
    singleBy<IMemoRepository, MockMemoRepository>()
}
