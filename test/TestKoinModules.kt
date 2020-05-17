package yoshixmk

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import yoshixmk.interfaces.controllers.IMemoController
import yoshixmk.interfaces.controllers.MemoController
import yoshixmk.usecases.service.IMemoService
import yoshixmk.usecases.service.MockMemoService

val testKoinModules = module(createdAtStart = true) {
    singleBy<IMemoController, MemoController>() // テストしない
    singleBy<IMemoService, MockMemoService>()
}
